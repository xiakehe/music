package com.example.study_music.com.xkh.music.player.ui;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;


import com.example.study_core.delegate.PermissionDelegate;
import com.example.study_music.com.xkh.music.base.MediaControlInterface;
import com.example.study_music.com.xkh.music.main.BaseActivity;
import com.example.study_music.com.xkh.music.player.MediaBrowserProvider;


public abstract class MediaBrowserDelegate extends PermissionDelegate implements MediaControlInterface {
    private static final String TAG = "MediaBrowserDelegate";
    protected MediaFragmentListener mediaBrowserProvider = null;
    protected BaseActivity _mActivity = null;
    protected MediaBrowserCompat browserCompat = null;


    public interface MediaFragmentListener extends MediaBrowserProvider {
        void onMediaItemSelected(MediaBrowserCompat.MediaItem item);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        if (context instanceof MediaFragmentListener) {
            Log.d(TAG, "mediaBrowserProvider");
            mediaBrowserProvider = (MediaFragmentListener) context;
        }
        if (context instanceof BaseActivity) {
            _mActivity = (BaseActivity) context;
        }

    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
        mediaBrowserProvider = null;
        _mActivity.setMediaControlInterface(null);
        _mActivity = null;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();

        if (_mActivity != null) {
            _mActivity.setMediaControlInterface(this);
        }

        browserCompat = mediaBrowserProvider.getMediaBrowser();
        if (browserCompat != null && browserCompat.isConnected()) {
            Log.d(TAG, "isConnected");
            onConnected(browserCompat);
        }


    }

    @Override
    public void hidePlaybackControls() {
        hidePlayBack();
    }

    protected abstract void hidePlayBack();

    protected abstract void showPlayBack();

    @Override
    public void showPlaybackControls() {
        if (browserCompat != null && browserCompat.isConnected()) {
            showPlayBack();
        } else {
            hidePlayBack();
        }
    }

    @Override
    public void onMediaControllerConnected() {
        Log.d(TAG, "onMediaControllerConnected");
        browserCompat = mediaBrowserProvider.getMediaBrowser();
        if (browserCompat != null && browserCompat.isConnected()) {
            Log.d(TAG, "isConnected");
            onConnected(browserCompat);
        }
    }

    protected abstract void onConnected(MediaBrowserCompat browserCompat);


}
