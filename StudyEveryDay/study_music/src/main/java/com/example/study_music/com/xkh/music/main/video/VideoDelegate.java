package com.example.study_music.com.xkh.music.main.video;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.study_core.delegate.buttom.BottomItemDelegate;
import com.example.study_music.R;

public class VideoDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

}
