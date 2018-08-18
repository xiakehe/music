package com.example.study_music.com.xkh.music.main.index.recommend.banner;

import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.study_music.R;

public class BannerViewCreater implements CBViewHolderCreator {
    @Override
    public Holder createHolder(View itemView) {
        return new BannerViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.launcher_banner;
    }
}
