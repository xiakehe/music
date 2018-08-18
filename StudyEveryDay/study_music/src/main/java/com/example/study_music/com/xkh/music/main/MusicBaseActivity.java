package com.example.study_music.com.xkh.music.main;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.study_core.util.ScreenUtils;
import com.example.study_core.util.ToastHelp;
import com.example.study_core.util.network.NetworkUtil;
import com.example.study_music.R;
import com.example.study_music.com.xkh.music.player.MusicService;
import com.example.study_music.com.xkh.music.player.ui.MediaBrowserDelegate;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class MusicBaseActivity extends SupportActivity implements MediaBrowserDelegate.MediaFragmentListener {

    private static final String TAG = "MusicBaseActivity";

    protected MediaBrowserCompat mMediaBrowser;
    private Unbinder mUnbind = null;
    private boolean isUserVisible = false;

    public abstract Object setLayout();


    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScreenUtils.setCustumDensity(this);
        super.onCreate(savedInstanceState);
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = LayoutInflater.from(this).inflate((Integer) setLayout(), null);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        mUnbind = ButterKnife.bind(this, rootView);
        setContentView(rootView);
        onBindView(savedInstanceState, rootView);
        //创建媒体浏览客户端（MediaBrowserCompat）
        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class), mConnectionCallback, null);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbind != null) {
            mUnbind.unbind();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isUserVisible = true;
    }

    @Override
    public void onMediaItemSelected(MediaBrowserCompat.MediaItem item) {
        MediaControllerCompat.getMediaController(this).getTransportControls()
                .playFromMediaId(item.getMediaId(), null);
    }


    /**
     * @param token is token
     * @throws RemoteException is run
     */
    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {

        Log.d(TAG, "connectToSession");

        MediaControllerCompat mediaController = new MediaControllerCompat(this, token);
        MediaControllerCompat.setMediaController(this, mediaController);

        mediaController.registerCallback(mMediaControllerCallback);
        if (shouldShowControls()) {
            showPlaybackControls();
        } else {
            Log.d(TAG, "connectionCallback.onConnected: " +
                    "hiding controls because metadata is null");
            hidePlaybackControls();
        }

        onMediaControllerConnected();

    }

    public MediaBrowserCompat getMediaBrowser() {
        return mMediaBrowser;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMediaBrowser.connect();

        Log.d(TAG, "Activity onStart");

    }


    @Override
    protected void onStop() {
        super.onStop();
        MediaControllerCompat mediaControllerCompat = MediaControllerCompat.getMediaController(this);
        if (mediaControllerCompat != null) {
            mediaControllerCompat.registerCallback(mMediaControllerCallback);
        }
        mMediaBrowser.disconnect();
        isUserVisible = false;
    }


    protected abstract void onMediaControllerConnected();

    protected abstract void onMusicMetadataChanged(MediaMetadataCompat metadata);

    protected abstract void onMusicPlaybackStateChanged(PlaybackStateCompat state);

    protected void hidePlaybackControls() {

    }


    protected void showPlaybackControls() {

    }


    private boolean shouldShowControls() {

        MediaControllerCompat mediaControllerCompat = MediaControllerCompat.getMediaController(this);
        if (mediaControllerCompat == null || mediaControllerCompat.getMetadata() == null || mediaControllerCompat.getPlaybackState() == null) {
            return false;
        }
        switch (mediaControllerCompat.getPlaybackState().getState()) {
            case PlaybackStateCompat.STATE_ERROR:
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                return false;
            default:
                return true;
        }

    }

    private final MediaControllerCompat.Callback mMediaControllerCallback = new MediaControllerCompat.Callback() {

        @Override
        public void onSessionEvent(String event, Bundle extras) {
            super.onSessionEvent(event, extras);

        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {

            Log.d(TAG, "onPlaybackStateChanged:" + state.getState());
            checkForUserVisibleErrors();

            if (shouldShowControls()) {
                showPlaybackControls();
            } else {
                Log.d(TAG, "mediaControllerCallback.onPlaybackStateChanged: " +
                        "hiding controls because state is " + state.getState());
                hidePlaybackControls();
            }
            if (isUserVisible) {
                onMusicPlaybackStateChanged(state);
            }

        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            super.onRepeatModeChanged(repeatMode);
            MusicBaseActivity.this.onRepeatModeChanged(repeatMode);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            checkForUserVisibleErrors();
            if (shouldShowControls()) {
                showPlaybackControls();
            } else {
                Log.d(TAG, "mediaControllerCallback.onMetadataChanged: " +
                        "hiding controls because metadata is null");
                hidePlaybackControls();
            }
            if (metadata == null) {
                Log.d(TAG, "metadata is null");
                return;
            }

            if (isUserVisible) {
                onMusicMetadataChanged(metadata);
            }
        }
    };

    protected void onRepeatModeChanged(int mode) {

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
                    && controller.getPlaybackState() != null
                    && controller.getPlaybackState().getState() == PlaybackStateCompat.STATE_ERROR
                    && controller.getPlaybackState().getErrorMessage() != null) {
                error = controller.getPlaybackState().getErrorMessage().toString();
                Log.e(TAG, error);
                ToastHelp.show(this, getString(R.string.play_error));
            }

        }

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
                hidePlaybackControls();
                checkForUserVisibleErrors();
            }
        }

        @Override
        public void onConnectionFailed() {
            Log.e(TAG, "could not connect media controller");
            hidePlaybackControls();
            checkForUserVisibleErrors();
        }
    };
}
