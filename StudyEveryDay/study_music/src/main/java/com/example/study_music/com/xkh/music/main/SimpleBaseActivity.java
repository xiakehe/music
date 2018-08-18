package com.example.study_music.com.xkh.music.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.example.study_core.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class SimpleBaseActivity extends SupportActivity {
    private Unbinder mUnbind = null;
    public abstract Object setLayout();


    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScreenUtils.setCustumDensity(this);
        super.onCreate(savedInstanceState);
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = LayoutInflater.from(this).inflate((Integer) setLayout(), null);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        mUnbind = ButterKnife.bind(this, rootView);
        setContentView(rootView);
        onBindView(savedInstanceState, rootView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbind != null) {
            mUnbind.unbind();
        }

    }
}
