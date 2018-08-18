package com.example.study_music.com.xkh.music.player;


import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.study_core.app.Study;
import com.example.study_core.util.file.ACache;
import com.example.study_music.R;
import com.example.study_music.com.xkh.music.bean.MusicDownloadInfo;
import com.example.study_music.com.xkh.music.player.model.LoadMusicDownLoadInfo;
import com.example.study_music.com.xkh.music.player.model.MusicProvider;
import com.example.study_music.com.xkh.music.player.model.MusicProviderSource;
import com.example.study_music.com.xkh.music.util.Contact;

public class PlaybackManager implements Playback.Callback {

    private static final String TAG = "PlaybackManager";
    // Action to thumbs up a media item
    //对某项媒体内容进行点赞
    private static final String CUSTOM_ACTION_THUMBS_UP = "com.example.android.uamp.THUMBS_UP";

    private MusicProvider mMusicProvider;
    private QueueManager mQueueManager;
    private Resources mResources;
    private Playback mPlayback;
    private PlaybackServiceCallback mServiceCallback;
    private MediaSessionCallback mMediaSessionCallback;

    PlaybackManager(PlaybackServiceCallback serviceCallback, Resources resources,
                    MusicProvider musicProvider, QueueManager queueManager,
                    Playback playback) {
        mMusicProvider = musicProvider;
        mServiceCallback = serviceCallback;
        mResources = resources;
        mQueueManager = queueManager;
        mMediaSessionCallback = new MediaSessionCallback();
        mPlayback = playback;
        mPlayback.setCallback(this);
    }

    public Playback getPlayback() {
        return mPlayback;
    }

    /**
     * 处理播放音乐的请求
     */
    public void handlePlayRequest() {
        Log.d(TAG, "handlePlayRequest: mState=" + mPlayback.getState());
        final MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
        if (currentMusic != null) {
            final String id = currentMusic.getDescription().getMediaId();
            MediaMetadataCompat music = mMusicProvider.getMusic(id);
            if (!TextUtils.isEmpty(music.getString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE)) && music.getLong(MediaMetadataCompat.METADATA_KEY_DURATION) != 0) {
                mServiceCallback.onPlaybackStart();
                mPlayback.play(currentMusic);
            } else {
                LoadMusicDownLoadInfo.startDownLoadMusic(id, new LoadMusicDownLoadInfo.LoadMusicInfo() {
                    @Override
                    public void onLoadMusicInfo(MusicDownloadInfo info) {
                        Log.d(TAG, "onLoadMusicInfo: info=" + info.toString());
                        MusicDownloadInfo.Bitrate bitrate = info.getBitrate();
                        if (bitrate != null) {
                            ACache.get(Study.getApplicationContext()).put(Contact.DOWNLOAD_MUSIC_INFO_CACHE, bitrate, Contact.LOCAL_MUSIC_CACHE_TIME);
                            mMusicProvider.updateMusicSource(id, MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, bitrate.getFile_link());
                            mMusicProvider.updateIntMusicSource(id, MediaMetadataCompat.METADATA_KEY_DURATION, bitrate.getFile_duration());
                        }
                    }

                    @Override
                    public void startLoad() {
                        mServiceCallback.onLoadResource();
                    }

                    @Override
                    public void loadError() {
                        mServiceCallback.onPlaybackStop();
                    }

                    @Override
                    public void loadSuccess() {
                        mServiceCallback.onPlaybackStart();
                        mQueueManager.updateMetadata();
                        mPlayback.play(mQueueManager.getCurrentMusic());
                    }
                });
            }

        } else {
            Log.e(TAG, "handlePlayRequest: currentMusic is null");

        }
    }



    /**
     * Handle a request to pause music
     * 处理暂停音乐的请求
     */
    public void handlePauseRequest() {
        Log.d(TAG, "handlePauseRequest: mState=" + mPlayback.getState());
        if (mPlayback.isPlaying()) {
            mPlayback.pause();
            mServiceCallback.onPlaybackStop();
            updatePlaybackState(null);
        }
    }

    /**
     * Handle a request to stop music
     * 处理停止音乐的请求
     *
     * @param withError Error message in case the stop has an unexpected cause. The error
     *                  message will be set in the PlaybackState and will be visible to
     *                  MediaController clients.
     */
    public void handleStopRequest(String withError) {
        Log.d(TAG, "handleStopRequest: mState=" + mPlayback.getState() + " error=" + withError);
        mPlayback.stop(true);
        mServiceCallback.onPlaybackStop();
        updatePlaybackState(withError);
    }

    //获取所有可用的动作命令
    private long getAvailableActions() {
        long actions =
                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
                        PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
        if (mPlayback.isPlaying()) {
            actions |= PlaybackStateCompat.ACTION_PAUSE;
        } else {
            actions |= PlaybackStateCompat.ACTION_PLAY;
        }
        return actions;
    }

    /**
     * 设置自定义的操作
     *
     * @param stateBuilder owner
     */
    private void setCustomAction(PlaybackStateCompat.Builder stateBuilder) {
        MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
        if (currentMusic == null) {
            return;
        }
        // Set appropriate "Favorite" icon on Custom action:
        //在自定义操作中设置适当的"喜爱"图标
        String mediaId = currentMusic.getDescription().getMediaId();
        if (mediaId == null) {
            return;
        }
        int favoriteIcon = mMusicProvider.isFavorite(mediaId) ?
                R.drawable.ic_favorite : R.drawable.ic_favorite;
        Bundle customActionExtras = new Bundle();
        stateBuilder.addCustomAction(new PlaybackStateCompat.CustomAction.Builder(
                CUSTOM_ACTION_THUMBS_UP, mResources.getString(R.string.favorite), favoriteIcon)
                .setExtras(customActionExtras)
                .build());
    }

    /**
     * Update the current media player state, optionally showing an error message.
     * 更新当前媒体播放器的状态，可选择是否显示错误信息
     *
     * @param error 如果不为null，错误信息将呈现给用户.
     */
    public void updatePlaybackState(String error) {
        Log.d(TAG, "updatePlaybackState, playback state=" + mPlayback.getState());
        long position = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN;
        if (mPlayback != null && mPlayback.isConnected()) {
            position = mPlayback.getCurrentStreamPosition();
            Log.d(TAG, "onPlaybackStatusChanged: position" + position);
        }

        //noinspection ResourceType
        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(getAvailableActions());

        setCustomAction(stateBuilder);
        int state = mPlayback.getState();

        // If there is an error message, send it to the playback state:
        if (error != null) {
            // Error states are really only supposed to be used for errors that cause playback to
            // stop unexpectedly and persist until the user takes action to fix it.
            stateBuilder.setErrorMessage(error);
            state = PlaybackStateCompat.STATE_ERROR;
        }
        //noinspection ResourceType
        stateBuilder.setState(state, position, 1.0f, SystemClock.elapsedRealtime());
        Log.d(TAG, "onPlaybackStatusChanged: elapsedRealtime" + SystemClock.elapsedRealtime());

        // Set the activeQueueItemId if the current index is valid.
        //如果当前索引是有效的
        MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
        if (currentMusic != null) {
            stateBuilder.setActiveQueueItemId(currentMusic.getQueueId());
        }

        mServiceCallback.onPlaybackStateUpdated(stateBuilder.build());

        if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_PAUSED) {
            mServiceCallback.onNotificationRequired();
        }
    }

    public interface PlaybackServiceCallback {
        void onPlaybackStart();

        void onLoadResource();

        void onNotificationRequired();

        void onPlaybackStop();

        void onPlaybackStateUpdated(PlaybackStateCompat newState);
    }


    public MediaSessionCompat.Callback getMediaSessionCallback() {
        return mMediaSessionCallback;
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPrepare() {
            super.onPrepare();
        }

        //点击播放按钮时触发
        //通过MediaControllerCompat.getTransportControls().play()触发
        @Override
        public void onPlay() {
            Log.d(TAG, "play");
            if (mQueueManager.getCurrentMusic() == null) {
                mQueueManager.setRandomQueue();
            }
            handlePlayRequest();
        }


        //播放指定队列媒体时触发
        //通过MediaControllerCompat.getTransportControls().onSkipToQueueItem(queueId)触发
        @Override
        public void onSkipToQueueItem(long queueId) {
            Log.d(TAG, "OnSkipToQueueItem:" + queueId);
            mQueueManager.setCurrentQueueItem(queueId);
            mQueueManager.updateMetadata();
        }

        //设置到指定进度时触发
        //通过MediaControllerCompat.getTransportControls().seekTo(position)触发
        @Override
        public void onSeekTo(long position) {
            Log.d(TAG, "onSeekTo:" + position);
            mPlayback.seekTo((int) position);
        }

        //播放指定媒体数据时触发
        //通过MediaControllerCompat.getTransportControls().playFromMediaId(mediaItem.getMediaId(), null)触发
        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            Log.d(TAG, "playFromMediaId mediaId:" + mediaId + "  extras=" + extras);
            mQueueManager.setQueueFromMusic(mediaId);
            handlePlayRequest();
        }

        //暂停时触发
        //通过MediaControllerCompat.getTransportControls().pause()触发
        @Override
        public void onPause() {
            Log.d(TAG, "pause. current state=" + mPlayback.getState());
            handlePauseRequest();
        }

        //停止播放时触发
        //通过MediaControllerCompat.getTransportControls().stop()触发
        @Override
        public void onStop() {
            Log.d(TAG, "stop. current state=" + mPlayback.getState());
            handleStopRequest(null);
        }

        //跳到下一首时触发
        //通过MediaControllerCompat.getTransportControls().skipToNext()触发
        @Override
        public void onSkipToNext() {
            Log.d(TAG, "skipToNext");
            if (mQueueManager.skipQueuePosition(1)) {
                handlePlayRequest();
            } else {
                handleStopRequest("Cannot skip");
            }
            mQueueManager.updateMetadata();
        }

        //跳到上一首时触发
        //通过MediaControllerCompat.getTransportControls().skipToPrevious()触发
        @Override
        public void onSkipToPrevious() {
            if (mQueueManager.skipQueuePosition(-1)) {
                handlePlayRequest();
            } else {
                handleStopRequest("Cannot skip");
            }
            mQueueManager.updateMetadata();
        }

        @Override
        public void onCustomAction(@NonNull String action, Bundle extras) {
            if (CUSTOM_ACTION_THUMBS_UP.equals(action)) {
                Log.i(TAG, "onCustomAction: favorite for current track");
                MediaSessionCompat.QueueItem currentMusic = mQueueManager.getCurrentMusic();
                if (currentMusic != null) {
                    String mediaId = currentMusic.getDescription().getMediaId();
                    if (mediaId != null) {
                        mMusicProvider.setFavorite(mediaId, !mMusicProvider.isFavorite(mediaId));
                    }
                }
                // playback state needs to be updated because the "Favorite" icon on the
                // custom action will change to reflect the new favorite state.
                updatePlaybackState(null);
            } else {
                Log.e(TAG, "Unsupported action: " + action);
            }
        }

        /**
         * Handle free and contextual searches.
         * <p/>
         * All voice searches on Android Auto are sent to this method through a connected
         * {@link android.support.v4.media.session.MediaControllerCompat}.
         * <p/>
         * Threads and async handling:
         * Search, as a potentially slow operation, should run in another thread.
         * <p/>
         * Since this method runs on the main thread, most apps with non-trivial metadata
         * should defer the actual search to another thread (for example, by using
         * an {@link AsyncTask} as we do here).
         **/
        @Override
        public void onPlayFromSearch(final String query, final Bundle extras) {
//            Log.d(TAG, "playFromSearch  query=", query, " extras=", extras);
//
//            mPlayback.setState(PlaybackStateCompat.STATE_CONNECTING);
//            mMusicProvider.retrieveMediaAsync(new MusicProvider.Callback() {
//                @Override
//                public void onMusicCatalogReady(boolean success) {
//                    if (!success) {
//                        updatePlaybackState("Could not load catalog");
//                    }
//
//                    boolean successSearch = mQueueManager.setQueueFromSearch(query, extras);
//                    if (successSearch) {
//                        handlePlayRequest();
//                        mQueueManager.updateMetadata();
//                    } else {
//                        updatePlaybackState("Could not find music");
//                    }
//                }
//            });
        }
    }


    @Override
    public void onCompletion() {
        // The media player finished playing the current song, so we go ahead
        // and start the next.
        //当音乐播放器播完了当前歌曲，则继续播放下一首
        if (mQueueManager.skipQueuePosition(1)) {
            handlePlayRequest();
            mQueueManager.updateMetadata();
        } else {
            // If skipping was not possible, we stop and release the resources:
            //若不可能跳到下一首音乐进行播放，则停止并释放资源
            handleStopRequest(null);
        }
    }

    @Override
    public void onPlaybackStatusChanged(int state) {
        Log.d(TAG, "onPlaybackStatusChanged: state" + state);
        updatePlaybackState(null);
    }

    @Override
    public void onError(String error) {
        updatePlaybackState(error);
    }

    @Override
    public void setCurrentMediaId(String mediaId) {
        Log.d(TAG, "setCurrentMediaId:" + mediaId);
        mQueueManager.setQueueFromMusic(mediaId);
    }
}
