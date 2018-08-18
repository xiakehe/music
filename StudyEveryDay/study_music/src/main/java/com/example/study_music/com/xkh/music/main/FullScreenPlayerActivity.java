package com.example.study_music.com.xkh.music.main;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.study_core.net.HttpCallbacks;
import com.example.study_core.net.HttpClient;
import com.example.study_core.util.ScreenUtils;
import com.example.study_core.util.ToastHelp;
import com.example.study_core.util.network.NetworkUtil;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.bean.Music;
import com.example.study_music.com.xkh.music.main.adapter.PlayPagerAdapter;
import com.example.study_music.com.xkh.music.player.MusicService;
import com.example.study_music.com.xkh.music.player.model.MusicProviderSource;
import com.example.study_music.com.xkh.music.util.CoverLoader;
import com.example.study_music.com.xkh.music.util.FileUtils;
import com.example.study_music.com.xkh.music.util.Preferences;
import com.example.study_music.com.xkh.music.view.AlbumCoverView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.wcy.lrcview.LrcView;
import me.yokeyword.fragmentation.SupportActivity;
import qiu.niorgai.StatusBarCompat;

import static com.example.study_music.com.xkh.music.util.Contact.LOOP;
import static com.example.study_music.com.xkh.music.util.Contact.SHUFFLE;
import static com.example.study_music.com.xkh.music.util.Contact.SINGLE;

public class FullScreenPlayerActivity extends SupportActivity implements LrcView.OnPlayClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "FullScreen";
    private static final long PROGRESS_UPDATE_INTERNAL = 1000;
    private static final long PROGRESS_UPDATE_INITIAL_INTERVAL = 100;
    private Music music;
    private Context mContext;
    private boolean isUserVisible;

    protected MediaBrowserCompat mMediaBrowser;


    private AlbumCoverView mAlbumCoverView;
    private LrcView mLrcViewSingle, mLrcViewFull;
    private SeekBar sbVolume, sb_progress;
    private List<View> mViewPagerContent;
    private AudioManager mAudioManager;
    private final Runnable mUpdateProgressTask = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    private final ScheduledExecutorService mExecutorService =
            Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> mScheduleFuture;
    private PlaybackStateCompat mLastPlaybackState;
    private Unbinder unbinder;

    @BindView(R2.id.iv_play_page_bg)
    ImageView mPlayPageBg = null;
    @BindView(R2.id.sb_progress)
    SeekBar mProgressBar = null;
    @BindView(R2.id.tv_current_time)
    TextView mStart = null;
    @BindView(R2.id.tv_total_time)
    TextView mEnd = null;
    @BindView(R2.id.tv_title)
    TextView mMusicName;
    @BindView(R2.id.tv_artist)
    TextView mMusicArtist;
    @BindView(R2.id.vp_play_page)
    ViewPager vpPlay = null;
    @BindView(R2.id.ll_content)
    LinearLayout llContent = null;
    @BindView(R2.id.iv_mode)
    ImageView ivMode;

    @OnClick(R2.id.iv_mode)
    void setPlayMode() {
        switchPlayMode();
    }


    @OnClick(R2.id.iv_back)
    void onClickBack() {
        finish();
    }

    @OnClick(R2.id.iv_prev)
    void onClickPrev() {
        prev();
    }

    @OnClick(R2.id.iv_next)
    void onClickNext() {
        next();
    }

    @BindView(R2.id.iv_play)
    ImageView mPlayOrPause = null;

    @OnClick(R2.id.iv_play)
    void onClickPlay() {

        PlaybackStateCompat stateObj = getController().getPlaybackState();
        final int state = stateObj == null ?
                PlaybackStateCompat.STATE_NONE : stateObj.getState();
        Log.d(TAG, "Button pressed, in state " + state);
        if (state == PlaybackStateCompat.STATE_PAUSED ||
                state == PlaybackStateCompat.STATE_STOPPED ||
                state == PlaybackStateCompat.STATE_NONE) {
            play();
            mPlayOrPause.setSelected(true);

        } else if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_BUFFERING ||
                state == PlaybackStateCompat.STATE_CONNECTING) {
            pause();
            mPlayOrPause.setSelected(false);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        StatusBarCompat.translucentStatusBar(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_player);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class), mConnectionCallback, null);
        initSystemBar();
        initViewPager();
        initPlayMode();

    }

    @Override
    protected void onStart() {
        super.onStart();
        sbVolume.setOnSeekBarChangeListener(this);
        mProgressBar.setOnSeekBarChangeListener(this);
        mMediaBrowser.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isUserVisible = true;
    }


    /**
     * 连接媒体浏览服务成功后的回调接口
     */

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {
            try {
                Log.d(TAG, "onConnected media controller");
                connectToSession(mMediaBrowser.getSessionToken());
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "e:" + e.toString());
                Log.e(TAG, "could not connect media controller");
                checkForUserVisibleErrors();
            }
        }

        @Override
        public void onConnectionFailed() {
            Log.e(TAG, "could not connect media controller");
            checkForUserVisibleErrors();
        }
    };

    /**
     * @param token is token
     * @throws RemoteException is run
     */
    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {

        Log.d(TAG, "connectToSession");

        MediaControllerCompat mediaController = new MediaControllerCompat(this, token);
        MediaControllerCompat.setMediaController(this, mediaController);

        mediaController.registerCallback(mMediaControllerCallback);
        MediaMetadataCompat metadataCompat = mediaController.getMetadata();
        PlaybackStateCompat state = mediaController.getPlaybackState();


        if (metadataCompat == null) {
            getSupportDelegate().pop();
        } else if (isUserVisible) {
            long i = mediaController.getMetadata().getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
            Log.e(TAG, "connectToSession  METADATA_KEY_DURATION" + i);
            Log.d(TAG, "connectToSession:" + metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID));
            updateMusicInfo(metadataCompat);
            updateDuration(metadataCompat);
            updatePlayState(state);

            updateProgress();
            if (state != null && (state.getState() == PlaybackStateCompat.STATE_PLAYING ||
                    state.getState() == PlaybackStateCompat.STATE_BUFFERING)) {
                scheduleSeekBarUpdate();
            }
            if (isPlaying()) {
                Intent update = new Intent(this, MusicService.class);
                update.setAction(MusicService.ACTION_CMD);
                update.putExtra(MusicService.CMD_NAME, MusicService.CMD_UPDATE);
                mContext.startService(update);
            }
        }


    }

    private void checkForUserVisibleErrors() {
        String error;
        // If offline, message is about the lack of connectivity:
        if (!NetworkUtil.isNetConnected(this)) {
            error = getString(R.string.network_error);
            ToastHelp.show(this, error);
        } else {
            // otherwise, if state is ERROR and metadata!=null, use playback state error message:
            MediaControllerCompat controller = MediaControllerCompat.getMediaController(this);
            if (controller != null
                    && controller.getMetadata() != null
                    && mLastPlaybackState != null
                    && mLastPlaybackState.getState() == PlaybackStateCompat.STATE_ERROR
                    && mLastPlaybackState.getErrorMessage() != null) {
                error = mLastPlaybackState.getErrorMessage().toString();
                Log.e(TAG, error);
                ToastHelp.show(this, getString(R.string.play_error));
            }

        }

    }

    private final MediaControllerCompat.Callback mMediaControllerCallback = new MediaControllerCompat.Callback() {

        @Override
        public void onSessionEvent(String event, Bundle extras) {
            super.onSessionEvent(event, extras);

        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {

            if (!isUserVisible) {
                return;
            }
            Log.d(TAG, "onPlaybackStateChanged:" + state.getState());
            checkForUserVisibleErrors();
            updatePlayState(state);
            updateProgress();


        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            super.onRepeatModeChanged(repeatMode);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            checkForUserVisibleErrors();
            if (metadata == null || !isUserVisible) {
                Log.d(TAG, "metadata is null");
                return;
            }
            updateMusicInfo(metadata);
            updateDuration(metadata);
            updateProgress();


        }
    };

    private MediaControllerCompat getController() {
        return MediaControllerCompat.getMediaController(this);
    }


    private void setPlayModeByUser(int mode) {
        MediaControllerCompat compat = getController();
        int a = compat.getRepeatMode();
        Log.d(TAG, "setPlayModeByUser:" + a);

        Log.d(TAG, "setPlayModeByUser: mode " + mode);
        if (mode == 1) {
            getController().getTransportControls().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ONE);
        } else if (mode == 0) {
            getController().getTransportControls().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_NONE);
        } else {
            getController().getTransportControls().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL);
        }


    }

    private final Handler mHandler = new Handler();


    private void scheduleSeekBarUpdate() {
        Log.e(TAG, "scheduleSeekBarUpdate  start");
        stopSeekBarUpdate();
        if (!mExecutorService.isShutdown()) {
            mScheduleFuture = mExecutorService.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(mUpdateProgressTask);
                        }
                    }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS);
        }
    }

    private void stopSeekBarUpdate() {
        Log.d(TAG, "stopSeekBarUpdate");
        if (mScheduleFuture != null) {
            mScheduleFuture.cancel(false);
        }
    }


    private void updateDuration(MediaMetadataCompat metadata) {
        Log.d(TAG, "updateDuration");

        if (metadata == null) {
            Log.e(TAG, "metadata is null ");
            return;
        }
        Log.d(TAG, "updateDuration called ");
        int duration = (int) metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        String url = metadata.getString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE);
        Log.d(TAG, "duration  " + duration + "url:" + url);
        mProgressBar.setMax(duration * 1000);
        mEnd.setText(DateUtils.formatElapsedTime(duration));
    }

    private void initPlayMode() {
        MediaControllerCompat compat = getController();
        if (compat != null) {
            int mode = compat.getRepeatMode();

            Log.d(TAG, "initPlayMode:" + mode);
            switch (mode) {
                case PlaybackStateCompat.REPEAT_MODE_NONE:
                    ivMode.setImageLevel(0);
                    break;
                case PlaybackStateCompat.REPEAT_MODE_ALL:
                    ivMode.setImageLevel(2);
                    break;
                case PlaybackStateCompat.REPEAT_MODE_ONE:
                    ivMode.setImageLevel(1);
                    break;
                default:
                    ivMode.setImageLevel(0);
            }
        } else {
            ivMode.setImageLevel(0);

        }

    }

    private void updatePlayState(PlaybackStateCompat stateCompat) {
        if (stateCompat == null) {
            Log.e(TAG, "stateCompat is null :");

            return;
        }
        mLastPlaybackState = stateCompat;

        Log.e(TAG, "mLastPlaybackState is :" + mLastPlaybackState.getState());
        int state = stateCompat.getState();
        switch (state) {
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                mPlayOrPause.setSelected(false);
                mAlbumCoverView.pause();
                stopSeekBarUpdate();
                break;
            case PlaybackStateCompat.STATE_PLAYING:
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_CONNECTING:
                mPlayOrPause.setSelected(true);
                mAlbumCoverView.start();
                scheduleSeekBarUpdate();
                break;

            case PlaybackStateCompat.STATE_PAUSED:
                mPlayOrPause.setSelected(false);
                mAlbumCoverView.pause();
                stopSeekBarUpdate();
                break;
            default:
                break;
        }
    }

    private void play() {
        MediaControllerCompat.TransportControls controls = getController().getTransportControls();
        controls.play();
        mAlbumCoverView.start();

    }

    private void pause() {
        MediaControllerCompat.TransportControls controls = getController().getTransportControls();
        controls.pause();
        mAlbumCoverView.pause();

    }

    private void next() {
        MediaControllerCompat.TransportControls controls = getController().getTransportControls();
        controls.skipToNext();
        controls.play();

    }

    private void prev() {
        MediaControllerCompat.TransportControls controls = getController().getTransportControls();
        controls.skipToPrevious();
        controls.play();
    }

    private void switchPlayMode() {
        int mode = Preferences.getPlayMode();
        Log.d(TAG, "save mode is " + mode);
        switch (mode) {
            case SHUFFLE:
                mode = SINGLE;

                ToastHelp.show(mContext, getString(R.string.mode_one));
                break;
            case SINGLE:
                mode = LOOP;

                ToastHelp.show(mContext, getString(R.string.mode_loop));
                break;
            case LOOP:
                mode = SHUFFLE;
                ToastHelp.show(mContext, getString(R.string.mode_shuffle));
                break;
            default:
                break;
        }
        Log.d(TAG, " will save mode is " + mode);

        Preferences.savePlayMode(mode);
        setPlayModeByUser(mode);
        initPlayMode();
    }

    private boolean isPlaying() {
        return mLastPlaybackState != null && mLastPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING;
    }


    private void initViewPager() {
        @SuppressLint("InflateParams") View coverView = LayoutInflater.from(this).inflate(R.layout.fragment_play_page_cover, null);
        @SuppressLint("InflateParams") View lrcView = LayoutInflater.from(this).inflate(R.layout.fragment_play_page_lrc, null);
        mAlbumCoverView = coverView.findViewById(R.id.album_cover_view);
        mLrcViewSingle = coverView.findViewById(R.id.lrc_view_single);
        mLrcViewFull = lrcView.findViewById(R.id.lrc_view_full);
        sbVolume = lrcView.findViewById(R.id.sb_volume);
        mAlbumCoverView.initNeedle(isPlaying());
        mLrcViewFull.setOnPlayClickListener(this);
        initVolume();

        mViewPagerContent = new ArrayList<>(2);
        mViewPagerContent.add(coverView);
        mViewPagerContent.add(lrcView);
        vpPlay.setAdapter(new PlayPagerAdapter(mViewPagerContent));
    }

    /**
     * 沉浸式状态栏
     */
    private void initSystemBar() {
        int top = ScreenUtils.getStatusBarHeight();
        llContent.setPadding(0, top, 0, 0);
    }

    private void initVolume() {
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager != null) {
            sbVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        }
    }


    private void updateMusicInfo(MediaMetadataCompat metadataCompat) {
        Log.d(TAG, "updateMusicInfo");

        if (metadataCompat == null) {
            return;
        }
        String title = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
        String url = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ART_URI);
        String artist = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
        String album = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ALBUM);
        String id = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);
        music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setTitle(title);
        music.setArtist(artist);
        music.setAlbum(album);
        music.setId(Long.valueOf(id));

        String albumFileName = FileUtils.getAlbumFileName(title, artist);
        File albumFile = new File(FileUtils.getAlbumDir(), albumFileName);
        music.setCoverPath(albumFile.getPath());
        if (!TextUtils.isEmpty(url) && !albumFile.exists()) {
            downloadAlbum(url, albumFileName);
        }else {
            mPlayPageBg.setImageBitmap(CoverLoader.get().loadBlur(music));
            mAlbumCoverView.setCoverBitmap(CoverLoader.get().loadRound(music));
        }
        mMusicName.setText(music.getTitle());


        mMusicArtist.setText(music.getArtist());


    }

    private void downloadAlbum(String url, String albumFileName) {


        HttpClient.downloadFile(url, FileUtils.getAlbumDir(), albumFileName, new HttpCallbacks<File>() {
            @Override
            public void onSuccess(File file) {
                if (mPlayPageBg != null && mAlbumCoverView != null && isUserVisible) {
                    mPlayPageBg.setImageBitmap(CoverLoader.get().loadBlur(music));
                    mAlbumCoverView.setCoverBitmap(CoverLoader.get().loadRound(music));
                }
            }

            @Override
            public void onFail(Exception e) {
                Log.e(TAG,"onFail"+e.toString());
            }

            @Override
            public void onFinish() {
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        isUserVisible = false;
        mAlbumCoverView.pause();
        stopSeekBarUpdate();
        MediaControllerCompat mediaControllerCompat = MediaControllerCompat.getMediaController(this);
        if (mediaControllerCompat != null) {
            mediaControllerCompat.registerCallback(mMediaControllerCallback);
        }
        mMediaBrowser.disconnect();

    }


    @Override
    public boolean onPlayClick(long time) {
        return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == sbVolume) {
            getController().setVolumeTo(progress, AudioManager.FLAG_PLAY_SOUND);
        } else {
            mStart.setText(DateUtils.formatElapsedTime(progress / 1000));
            //  getController().getTransportControls().seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar == mProgressBar) {
            stopSeekBarUpdate();

        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == mProgressBar) {
            getController().getTransportControls().seekTo(seekBar.getProgress());
            scheduleSeekBarUpdate();
        }

    }

    private void updateProgress() {
        if (mLastPlaybackState == null) {
            Log.e(TAG, "mLastPlaybackState is null");
            return;
        }
        long currentPosition = mLastPlaybackState.getPosition();
        if (mLastPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
            // Calculate the elapsed time between the last position update and now and unless
            // paused, we can assume (delta * speed) + current position is approximately the
            // latest position. This ensure that we do not repeatedly call the getPlaybackState()
            // on MediaControllerCompat.
            long timeDelta = SystemClock.elapsedRealtime() -
                    mLastPlaybackState.getLastPositionUpdateTime();
            Log.e(TAG, "timeDelta is " + timeDelta);
            currentPosition += (int) timeDelta * mLastPlaybackState.getPlaybackSpeed();
        }
        Log.e(TAG, "getPosition is " + mLastPlaybackState.getPosition());
        Log.e(TAG, "currentPosition is " + currentPosition);
        mProgressBar.setProgress((int) currentPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        stopSeekBarUpdate();
        mExecutorService.shutdown();
    }
}
