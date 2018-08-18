package com.example.study_music.com.xkh.music.main.personal.local;

import android.support.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.study_music.R;
import com.example.study_music.com.xkh.music.bean.Music;
import com.example.study_music.com.xkh.music.util.FileUtils;

import java.util.List;

public class SingleMusicAdapter extends BaseQuickAdapter<Music, BaseViewHolder> {


    public SingleMusicAdapter(int layoutResId, @Nullable List<Music> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Music item) {
        helper.setText(R.id.tv_title, item.getTitle());
        String artist = FileUtils.getArtistAndAlbum(item.getArtist(), item.getAlbum());
        helper.setText(R.id.tv_artist, artist);
        helper.addOnClickListener(R.id.iv_more);
        helper.setVisible(R.id.iv_playing,false);
    }
}
