package com.example.study_ui;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.request.RequestOptions;

import me.wcy.lrcview.LrcView;

public class GlideUtil {
    public static final RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.default_cover)
            .error(R.drawable.ic_error)
            .dontAnimate();


}
