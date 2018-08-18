package com.example.study_music.com.xkh.music.main;


import android.content.ComponentName;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.example.study_music.com.xkh.music.base.MediaControlInterface;
import com.example.study_music.com.xkh.music.player.MusicService;
import com.example.study_music.com.xkh.music.player.ui.MediaBrowserDelegate;

import org.jetbrains.annotations.Nullable;


public abstract class BaseActivity extends RootDelegateBaseActivity implements MediaBrowserDelegate.MediaFragmentListener {

    private static final String TAG = "BaseActivity";
    protected MediaBrowserCompat mMediaBrowser;
    protected MediaControlInterface mediaControlInterface;

    public void setMediaControlInterface(MediaControlInterface mediaControlInterface) {
        this.mediaControlInterface = mediaControlInterface;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class), mConnectionCallback, null);
    }

    @Override
    public void onMediaItemSelected(MediaBrowserCompat.MediaItem item) {
        MediaControllerCompat.getMediaController(this).getTransportControls()
                .playFromMediaId(item.getMediaId(), null);
    }

    @Override
    public MediaBrowserCompat getMediaBrowser() {
        return mMediaBrowser;
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");

        super.onStart();
        if (mMediaBrowser != null) {
            mMediaBrowser.connect();
            Log.d(TAG, "mMediaBrowser Start connect");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaBrowser != null) {
            mMediaBrowser.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaBrowser = null;
        mediaControlInterface = null;
        mConnectionCallback = null;
        mMediaControllerCallback = null;

    }

    /**
     * 连接媒体浏览服务成功后的回调接口
     */

    private MediaBrowserCompat.ConnectionCallback mConnectionCallback = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {
            Log.d(TAG, "onConnected media controller");
            connectToSession(mMediaBrowser.getSessionToken());
        }

        @Override
        public void onConnectionFailed() {
            Log.e(TAG, "could not connect media controller");
            connectError();
        }
    };

    protected void connectError() {

    }

    protected void connectToSession(MediaSessionCompat.Token sessionToken) {

        MediaControllerCompat mediaController;
        try {
            mediaController = new MediaControllerCompat(this, sessionToken);
            MediaControllerCompat.setMediaController(this, mediaController);
            mediaController.registerCallback(mMediaControllerCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        if (shouldShowControls()) {
            showControls();
        } else {
            Log.d(TAG, "connectionCallback.onConnected: " +
                    "hiding controls because metadata is null");
            hideControls();
        }
        if (mediaControlInterface != null) {
            mediaControlInterface.onMediaControllerConnected();
        }


    }


    /**
     * Check if the MediaSession is active and in a "playback-able" state
     * (not NONE and not STOPPED).
     *
     * @return true if the MediaSession's state requires playback controls to be visible.
     */
    protected boolean shouldShowControls() {
        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(this);
        if (mediaController == null ||
                mediaController.getMetadata() == null ||
                mediaController.getPlaybackState() == null) {
            return false;
        }
        switch (mediaController.getPlaybackState().getState()) {
            case PlaybackStateCompat.STATE_ERROR:
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                return false;
            default:
                return true;
        }
    }

    private void showControls() {
        if (mediaControlInterface != null) {
            mediaControlInterface.showPlaybackControls();
        }
    }

    private void hideControls() {
        if (mediaControlInterface != null) {
            mediaControlInterface.hidePlaybackControls();
        }
    }

    // Callback that ensures that we are showing the controls
    /**
     * 媒体控制器控制播放过程中的回调接口
     * 这里主要是根据当前播放的状态决定PlaybackControlsFragment是否显示
     */
    private MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
                    if (shouldShowControls()) {
                        showControls();
                    } else {
                        Log.d(TAG, "mediaControllerCallback.onPlaybackStateChanged: " +
                                "hiding controls because state is " + state.getState());
                        hideControls();
                    }
                }

                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    if (shouldShowControls()) {
                        showControls();
                    } else {
                        Log.d(TAG, "mediaControllerCallback.onMetadataChanged: " +
                                "hiding controls because metadata is null");
                        hideControls();
                    }
                }
            };

}
