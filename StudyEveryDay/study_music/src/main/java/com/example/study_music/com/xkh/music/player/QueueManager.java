package com.example.study_music.com.xkh.music.player;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.study_music.R;
import com.example.study_music.com.xkh.music.player.model.MusicProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueManager {

    private static final String TAG = "QueueManager";

    private MusicProvider mMusicProvider;
    private MetadataUpdateListener mListener;
    private Resources mResources;

    // "Now playing" queue:
    //当前播放队列
    private List<MediaSessionCompat.QueueItem> mPlayingQueue;
    private int mCurrentIndex;

    public interface MetadataUpdateListener {
        void onMetadataChanged(MediaMetadataCompat metadata);//媒体数据变更时调用

        void onMetadataRetrieveError();//媒体数据检索失败时调用

        void onCurrentQueueIndexUpdated(int queueIndex);//当前播放索引变更时调用

        void onQueueUpdated(String title, List<MediaSessionCompat.QueueItem> newQueue);//当前播放队列变更时调用
    }

    /**
     * @param musicProvider 数据源提供者
     * @param resources     系统资源
     * @param listener      播放数据更新的回调接口
     */
    public QueueManager(@NonNull MusicProvider musicProvider,
                        @NonNull Resources resources,
                        @NonNull MetadataUpdateListener listener) {
        this.mMusicProvider = musicProvider;
        this.mListener = listener;
        this.mResources = resources;

        //mPlayingQueue是线程安全的
        mPlayingQueue = Collections.synchronizedList(new ArrayList<MediaSessionCompat.QueueItem>());
        mCurrentIndex = 0;
    }

    /**
     * 设置当前的队列索引值
     *
     * @param index
     */
    private void setCurrentQueueIndex(int index) {
        if (index >= 0 && index < mPlayingQueue.size()) {
            mCurrentIndex = index;
            mListener.onCurrentQueueIndexUpdated(mCurrentIndex);
        }
    }


    public boolean setCurrentQueueItem(long queueId) {
        // set the current index on queue from the queue Id:
        int index = QueueHelper.getMusicIndexOnQueue(mPlayingQueue, queueId);
        setCurrentQueueIndex(index);
        return index >= 0;
    }

    public boolean setCurrentQueueItem(String mediaId) {
        // set the current index on queue from the music Id:
        int index = QueueHelper.getMusicIndexOnQueue(mPlayingQueue, mediaId);
        setCurrentQueueIndex(index);
        return index >= 0;
    }

    public boolean skipQueuePosition(int amount) {
        int index = mCurrentIndex + amount;
        if (index < 0) {
            index = 0;
        } else {
            index %= mPlayingQueue.size();
        }
        if (!QueueHelper.isIndexPlayable(index, mPlayingQueue)) {
            Log.e(TAG, "Cannot increment queue index by " + amount +
                    ". Current=" + mCurrentIndex + " queue length=" + mPlayingQueue.size());
            return false;
        }
        mCurrentIndex = index;
        return true;
    }

    /**
     * 设置播放队列为随机队列
     */
    public void setRandomQueue() {
        setCurrentQueue(mResources.getString(R.string.random_queue_title),
                QueueHelper.getRandomQueue(mMusicProvider));
        updateMetadata();
    }

    public void setQueueFromMusic(String mediaId) {
        Log.d(TAG, "setQueueFromMusic:" + mediaId);

        // The mediaId used here is not the unique musicId. This one comes from the
        // MediaBrowser, and is actually a "hierarchy-aware mediaID": a concatenation of
        // the hierarchy in MediaBrowser and the actual unique musicID. This is necessary
        // so we can build the correct playing queue, based on where the track was
        // selected from.
        //这里使用的mediaId并不仅限于作为唯一的音乐识别Id，mediaId依赖于MediaBrowser，实际上它是一种层次清晰的mediaId：
        //在MediaBrowser中它具有 表明层级之间是如何关联的 以及 作为实际音乐唯一识别id 的作用
        //使用mediaId的必要之处在于我们可以根据 所选择的播放轨迹 来构建正确的播放队列
        boolean canReuseQueue = false;
        if (isSameBrowsingCategory(mediaId)) {
            canReuseQueue = setCurrentQueueItem(mediaId);
        }
        if (!canReuseQueue) {
            String queueTitle = mMusicProvider.getTypeByMusic(mediaId);
            setCurrentQueue(queueTitle,
                    QueueHelper.getPlayingQueue(mediaId, mMusicProvider), mediaId);
        }
        updateMetadata();
    }


    public int getCurrentQueueSize() {
        if (mPlayingQueue == null) {
            return 0;
        }
        return mPlayingQueue.size();
    }

    private boolean isSameBrowsingCategory(String mediaId) {
        if (getCurrentMusic() == null) {
            return false;
        }
        String typeCur = mMusicProvider.getTypeByMusic(getCurrentMusic().getDescription().getMediaId());
        String typeMedia = mMusicProvider.getTypeByMusic(mediaId);

        return TextUtils.equals(typeCur, typeMedia);
    }

    /**
     * 更新媒体数据
     */
    public void updateMetadata() {
        MediaSessionCompat.QueueItem currentMusic = getCurrentMusic();
        if (currentMusic == null) {
            mListener.onMetadataRetrieveError();
            return;
        }
        final String musicId = currentMusic.getDescription().getMediaId();
        MediaMetadataCompat metadata = mMusicProvider.getMusic(musicId);
        if (metadata == null) {
            throw new IllegalArgumentException("Invalid musicId " + musicId);
        }

        mListener.onMetadataChanged(metadata);

        //在MediaSession中设置适当的音乐专辑封面插图，以便在锁屏界面和其他地方显示
        if (metadata.getDescription().getIconBitmap() == null &&
                metadata.getDescription().getIconUri() != null) {
            String albumUri = metadata.getDescription().getIconUri().toString();
            //异步获取音乐专辑封面图片，通过回调将图片位图等信息返回来
//            AlbumArtCache.getInstance().fetch(albumUri, new AlbumArtCache.FetchListener() {
//                @Override
//                public void onFetched(String artUrl, Bitmap bitmap, Bitmap icon) {
//                    mMusicProvider.updateMusicArt(musicId, bitmap, icon);
//
//                    // If we are still playing the same music, notify the listeners:
//                    MediaSessionCompat.QueueItem currentMusic = getCurrentMusic();
//                    if (currentMusic == null) {
//                        return;
//                    }
//                    String currentPlayingId = MediaIDHelper.extractMusicIDFromMediaID(
//                            currentMusic.getDescription().getMediaId());
//                    if (musicId.equals(currentPlayingId)) {
//                        mListener.onMetadataChanged(mMusicProvider.getMusic(currentPlayingId));
//                    }
//                }
//            });
        }
    }

    /**
     * 通过mCurrentIndex获取当前播放的音乐
     *
     * @return
     */
    public MediaSessionCompat.QueueItem getCurrentMusic() {
        if (!QueueHelper.isIndexPlayable(mCurrentIndex, mPlayingQueue)) {
            return null;
        }
        return mPlayingQueue.get(mCurrentIndex);
    }

    protected void setCurrentQueue(String title, List<MediaSessionCompat.QueueItem> newQueue) {
        setCurrentQueue(title, newQueue, null);
    }

    /**
     * 设置当前播放队列
     *
     * @param title
     * @param newQueue       新的播放队列
     * @param initialMediaId 初始的mediaId
     */
    protected void setCurrentQueue(String title, List<MediaSessionCompat.QueueItem> newQueue,
                                   String initialMediaId) {
        if (newQueue != null) {
            mPlayingQueue = newQueue;
        }

        int index = 0;
        if (initialMediaId != null) {
            index = QueueHelper.getMusicIndexOnQueue(mPlayingQueue, initialMediaId);
            Log.d(TAG, "index:" + index);
        }
        mCurrentIndex = Math.max(index, 0);
        mListener.onQueueUpdated(title, newQueue);
    }
}
