package com.example.study_music.com.xkh.music.main.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.example.study_core.app.AccountManager;
import com.example.study_core.delegate.web.WebDelegateImpl;
import com.example.study_core.delegate.web.route.RouteKeys;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.main.SimpleBaseActivity;
import com.example.study_music.com.xkh.music.view.MusicToolBar;
import com.example.study_music.com.xkh.music.view.ToolBarType;

import butterknife.BindView;

public class WebActivity extends SimpleBaseActivity {

    @BindView(R2.id.tool_bar)
    MusicToolBar toolBar = null;
    private String title, url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        title = getIntent().getStringExtra(RouteKeys.TITLE.name());
        url = getIntent().getStringExtra(RouteKeys.URL.name());
        super.onCreate(savedInstanceState);

    }

    @Override
    public Object setLayout() {
        return R.layout.activity_webview;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        if (TextUtils.isEmpty(url)) {
            finish();
        }
        if (TextUtils.isEmpty(title)) {

            title = getString(R.string.app_name);
        }
        WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        getSupportDelegate().loadRootFragment(R.id.container_web, webDelegate);
        toolBar.setmLeftTitle(title);
        toolBar.setmNormalSearchClickListener(new MusicToolBar.ToolBarNormalSearchClickListener() {
            @Override
            public void onReturn() {
                finish();
            }

            @Override
            public void onClickSearch() {

            }
        });
        toolBar.reInitViewByMode(ToolBarType.LEFT, AccountManager.isSign());

    }
}
