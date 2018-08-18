package com.example.study_music.com.xkh.music.main.index.recommend.banner;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.study_core.app.Study;
import com.example.study_core.util.log.StudyLogger;
import com.example.study_music.R;
import com.example.study_ui.GlideUtil;
import com.example.study_ui.glide.GlideRoundTransform;
import com.example.study_ui.recycler.MultipleFields;
import com.example.study_ui.recycler.MultipleItemEntity;

public class BannerViewHolder extends Holder<String> {


    private AppCompatImageView imageView;

    public BannerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.banner_image);
    }

    @Override
    public void updateUI(String data) {
        Glide.with(imageView.getContext())
                .load(data)
                .apply(GlideUtil.REQUEST_OPTIONS)
                .into(imageView);
    }
}
