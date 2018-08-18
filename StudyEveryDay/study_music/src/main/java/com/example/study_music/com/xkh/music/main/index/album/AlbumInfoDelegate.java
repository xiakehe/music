package com.example.study_music.com.xkh.music.main.index.album;


import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.study_core.delegate.BaseDelegate;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.player.ui.MediaBrowserDelegate;
import com.example.study_music.com.xkh.music.util.Contact;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;


public class AlbumInfoDelegate extends MediaBrowserDelegate implements AlbumInfoView{

    private String albumId = null;

    @BindView(R2.id.iv_album_info_bg)
    ImageView bgView;

    @BindView(R2.id.iv_album_info_pic)
    ImageView albumPic;

    @BindView(R2.id.tv_album_info_title)
    TextView albumName;

    @BindView(R2.id.tv_album_info_artist)
    TextView albumArtist;

    @BindView(R2.id.tv_album_info_public_time)
    TextView albumTime;

    @BindView(R2.id.tv_album_info_comment)
    TextView albumComment;

    @BindView(R2.id.tv_album_info_collection)
    TextView albumCollection;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumId = getArguments().getString(Contact.PARAM_ALBUM_ID, "");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_album_info;
    }

    @Override
    protected void hidePlayBack() {

    }

    @Override
    protected void showPlayBack() {

    }

    @Override
    protected void onConnected(MediaBrowserCompat browserCompat) {

        //browserCompat.subscribe();
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("AlbumInfoDelegate", "onDestroyView");
    }

    @NotNull
    @Override
    public BaseDelegate getHostDelegate() {
        return this;
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void onLoadError(@NotNull String error) {
    }

    @Override
    public void loadAlbumMusicList() {

    }
}
