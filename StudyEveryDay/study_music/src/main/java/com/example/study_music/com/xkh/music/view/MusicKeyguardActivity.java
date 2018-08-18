package com.example.study_music.com.xkh.music.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.study_core.net.HttpCallbacks;
import com.example.study_core.net.HttpClient;
import com.example.study_core.util.ScreenUtils;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.bean.Music;
import com.example.study_music.com.xkh.music.main.MusicBaseActivity;
import com.example.study_music.com.xkh.music.util.CoverLoader;
import com.example.study_music.com.xkh.music.util.FileUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;


public class MusicKeyguardActivity extends MusicBaseActivity {


    private static final String TAG = "MusicKeyguardActivity";

    @BindView(R2.id.iv_bg_keyguard)
    ImageView bgImage = null;

    @BindView(R2.id.iv_play)
    ImageView mPlayOrPause = null;

    @BindView(R2.id.tv_music_keyguard_title)
    TextView mTitle = null;

    @BindView(R2.id.tv_music_keyguard_artist)
    TextView mArtist = null;


    @OnClick(R2.id.iv_prev)
    void onClickPrev() {
        prev();
    }

    @OnClick(R2.id.iv_next)
    void onClickNext() {
        next();
    }

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
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this, true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    /**
     * 沉浸式状态栏
     */
    private void initSystemBar() {
        int top = ScreenUtils.getStatusBarHeight();
        //  llContent.setPadding(0, top, 0, 0);
    }

    @Override
    public Object setLayout() {
        return R.layout.activity_music_keyguard;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initSystemBar();
    }

    @Override
    protected void onMediaControllerConnected() {

        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(this);
        MediaMetadataCompat metadataCompat = mediaController.getMetadata();
        updateMusicInfo(metadataCompat);

    }

    @Override
    protected void onMusicMetadataChanged(MediaMetadataCompat metadata) {
        updateMusicInfo(metadata);

    }

    @Override
    protected void onMusicPlaybackStateChanged(PlaybackStateCompat state) {

        Log.d(TAG, "onPlaybackStateChanged:" + state.getState());
        updatePlayState(state);
    }

    private void updateMusicInfo(MediaMetadataCompat metadataCompat) {
        Log.d(TAG, "updateMusicInfo");

        if (metadataCompat == null) {
            return;
        }
        String title = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_TITLE);
        String url = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ART_URI);
        String artist = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
        String id = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setTitle(title);
        music.setArtist(artist);
        music.setId(Long.valueOf(id));

        String albumFileName = FileUtils.getAlbumFileName(title, artist);
        File albumFile = new File(FileUtils.getAlbumDir(), albumFileName);
        music.setCoverPath(albumFile.getPath());
        if (!TextUtils.isEmpty(url) && !albumFile.exists()) {
            downloadAlbum(url, albumFileName, music);
        } else {
            bgImage.setImageBitmap(CoverLoader.get().loadBlur(music));
        }
        mTitle.setText(music.getTitle());
        mArtist.setText(music.getArtist());


    }


    private void downloadAlbum(String url, String albumFileName, final Music music) {


        HttpClient.downloadFile(url, FileUtils.getAlbumDir(), albumFileName, new HttpCallbacks<File>() {
            @Override
            public void onSuccess(File file) {
                if (bgImage != null) {
                    bgImage.setImageBitmap(CoverLoader.get().loadBlur(music));
                }
            }

            @Override
            public void onFail(Exception e) {
                Log.e(TAG, "onFail" + e.toString());
            }

            @Override
            public void onFinish() {
            }
        });
    }

    private void updatePlayState(PlaybackStateCompat stateCompat) {
        if (stateCompat == null) {
            Log.e(TAG, "stateCompat is null :");

            return;
        }

        Log.e(TAG, "updatePlayState is :" + stateCompat.getState());
        int state = stateCompat.getState();
        switch (state) {
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                mPlayOrPause.setSelected(false);
                break;
            case PlaybackStateCompat.STATE_PLAYING:
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_CONNECTING:
                mPlayOrPause.setSelected(true);
                break;

            case PlaybackStateCompat.STATE_PAUSED:
                mPlayOrPause.setSelected(false);
                break;
            default:
                break;
        }
    }

    private void play() {
        MediaControllerCompat.TransportControls controls = getController().getTransportControls();
        controls.play();

    }

    private void pause() {
        MediaControllerCompat.TransportControls controls = getController().getTransportControls();
        controls.pause();

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

    public MediaControllerCompat getController() {
        return MediaControllerCompat.getMediaController(this);
    }
}
