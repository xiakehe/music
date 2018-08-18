package com.example.study_music.com.xkh.music.main.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.newmvp.BaseMVPDelegate;
import com.example.study_core.app.Study;
import com.example.study_core.delegate.buttom.BottomItemDelegate;

import com.example.study_core.rx.Event;
import com.example.study_core.rx.RxJava2Manager;
import com.example.study_core.util.ToastHelp;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.bean.User;
import com.example.study_music.com.xkh.music.main.FullScreenPlayerActivity;
import com.example.study_music.com.xkh.music.main.index.categry.SortDelegate;
import com.example.study_music.com.xkh.music.main.index.good.GoodDelegate;
import com.example.study_music.com.xkh.music.main.index.living.LivingDelegate;
import com.example.study_music.com.xkh.music.main.index.recommend.RecommendDelegate;
import com.example.study_music.com.xkh.music.sign.SignInDelegate;
import com.example.study_music.com.xkh.music.view.MusicToolBar;
import com.example.study_music.com.xkh.music.view.ToolBarType;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class IndexDelegate extends BottomItemDelegate implements MusicToolBar.ToolBarOnClickListener, MusicToolBar.ToolBarOnClickListener.onFocusChangeListener {
    @BindView(R2.id.vp_home)
    ViewPager viewPager = null;

    @BindView(R2.id.tab_home)
    TabLayout tabLayout = null;
    @BindView(R2.id.tool_bar_layout)
    MusicToolBar toolBar = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initViews();
        initToolBar();

    }

    protected void notifySignInOut(User user) {

        if (toolBar != null) {
            toolBar.reInitViewByMode(ToolBarType.HOME, Study.isUserLogin());
        }
    }

    private void initToolBar() {
        toolBar.setToolBarOnClickListener(this);
        toolBar.setOnFocusChangeListener(this);
        toolBar.reInitViewByMode(ToolBarType.HOME, Study.isUserLogin());
        RxJava2Manager.getRxJava2ManagerInstance().ConsumeEvent(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                if (o instanceof Event && ((Event) o).getEventInfo() == Event.EVENT_INFO.SIGN_IN) {

                    notifySignInOut((User) ((Event) o).getObj());
                } else if (((Event) o).getEventInfo() == Event.EVENT_INFO.SIGN_OUT) {
                    notifySignInOut(null);

                }
            }
        });

    }


    private void initViews() {

        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("分类");
        tabs.add("推荐");
        tabs.add("精品");
        tabs.add("直播");

        ArrayList<BaseMVPDelegate> studyDelegates = new ArrayList<>();
        studyDelegates.add(new SortDelegate());
        studyDelegates.add(new RecommendDelegate());
        studyDelegates.add(new GoodDelegate());
        studyDelegates.add(new LivingDelegate());

        viewPager.setAdapter(new IndexDelegateViewPagerAdapter
                .IndexDelegateViewPagerAdapterBuilder().addTabList(tabs)
                .addFragmentManager(getChildFragmentManager())
                .addDelegates(studyDelegates)
                .build());
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public void onClickLogin() {
       // startActivity(new Intent(getActivity(), SignInActivity.class));
        getParentDelegate().start(new SignInDelegate());
    }

    @Override
    public void onClickDownLoad() {
        ToastHelp.showLong(mContext, "onClickDownLoad");
    }

    @Override
    public void onClickTime() {
        Intent intent = new Intent(getActivity(), FullScreenPlayerActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClickEmail() {
        ToastHelp.showLong(mContext, "onClickEmail");

    }

    @Override
    public void onClickSetting() {
        ToastHelp.showLong(mContext, "onClickSetting");

    }

    @Override
    public void onClickSearch() {
        ToastHelp.showLong(mContext, "onClickSearch");

    }

    @Override
    public void onFocusChangeListen(boolean hasFocus) {
        ToastHelp.showLong(mContext, "onFocusChangeListen");

    }
}
