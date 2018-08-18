package com.example.study_music.com.xkh.music.main.list.info;

import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.study_music.R;
import com.example.study_ui.GlideUtil;

import java.util.List;

public class MusicTypeAdapter extends BaseQuickAdapter<MediaBrowserCompat.MediaItem, BaseViewHolder> {


    MusicTypeAdapter(int layoutResId, @Nullable List<MediaBrowserCompat.MediaItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaBrowserCompat.MediaItem item) {

        MediaDescriptionCompat descriptionCompat = item.getDescription();


        helper.setText(R.id.tv_title, descriptionCompat.getTitle());
        helper.setText(R.id.tv_artist, descriptionCompat.getSubtitle());
        helper.addOnClickListener(R.id.iv_more);
        Glide.with(mContext)
                .load(descriptionCompat.getDescription())
                .apply(GlideUtil.REQUEST_OPTIONS)
                .into((ImageView) helper.getView(R.id.iv_playing));
    }
}
