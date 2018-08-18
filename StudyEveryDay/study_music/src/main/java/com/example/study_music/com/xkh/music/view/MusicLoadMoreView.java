package com.example.study_music.com.xkh.music.view;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.example.study_music.R;

public class MusicLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.music_load_more_view;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    public boolean isLoadEndGone() {
        return true;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return 0;
    }
}
