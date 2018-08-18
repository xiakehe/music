package com.example.study_music.com.xkh.music.main.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.newmvp.BaseMVPDelegate;
import com.example.study_core.app.AccountManager;

import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.main.index.IndexDelegateViewPagerAdapter;
import com.example.study_music.com.xkh.music.main.index.categry.SortDelegate;
import com.example.study_music.com.xkh.music.main.personal.local.SingleMusicDelegate;
import com.example.study_music.com.xkh.music.view.MusicToolBar;
import com.example.study_music.com.xkh.music.view.ToolBarType;

import java.util.ArrayList;

import butterknife.BindView;

public class LocalMusicDelegate extends BaseMVPDelegate implements MusicToolBar.ToolBarNormalSearchClickListener {

    @BindView(R2.id.vp_home)
    ViewPager viewPager = null;

    @BindView(R2.id.tab_home)
    TabLayout tabLayout = null;
    @BindView(R2.id.tool_bar_layout)
    MusicToolBar toolBar = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_local_music;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initToolBar();
        initViews();
    }

    private void initToolBar() {
        toolBar.setCenterText("本地音乐");
        toolBar.reInitViewByMode(ToolBarType.NORMAL, AccountManager.isSign());
        toolBar.setmNormalSearchClickListener(this);

    }

    private void initViews() {

        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("单曲");
        tabs.add("歌手");
        tabs.add("专辑");
        tabs.add("文件夹");

        ArrayList<BaseMVPDelegate> studyDelegates = new ArrayList<>();
        studyDelegates.add(new SingleMusicDelegate());
        studyDelegates.add(new SortDelegate());
        studyDelegates.add(new SortDelegate());
        studyDelegates.add(new SortDelegate());

        viewPager.setAdapter(new IndexDelegateViewPagerAdapter
                .IndexDelegateViewPagerAdapterBuilder().addTabList(tabs)
                .addFragmentManager(getChildFragmentManager())
                .addDelegates(studyDelegates)
                .build());
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onReturn() {
        getActivity().finish();
    }


    @Override
    public void onClickSearch() {

    }
}
