package com.example.study_music.com.xkh.music.main.index.good;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.newmvp.BaseMVPDelegate;
import com.example.study_music.R;

public class GoodDelegate extends BaseMVPDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_good;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

}
