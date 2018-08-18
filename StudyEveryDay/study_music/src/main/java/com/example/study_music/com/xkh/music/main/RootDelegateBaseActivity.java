package com.example.study_music.com.xkh.music.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.example.newmvp.BaseMVPActivity;
import com.example.newmvp.BaseMVPDelegate;
import com.example.study_music.R;

public abstract class RootDelegateBaseActivity extends BaseMVPActivity {

    public abstract BaseMVPDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if (savedInstanceState == null) {
            getSupportDelegate().loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        getSupportDelegate().onDestroy();
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

}
