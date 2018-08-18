package com.example.xiakehe.studyeveryday.app;


import com.example.newmvp.BaseMVPDelegate;
import com.example.study_music.com.xkh.music.main.BaseActivity;
import com.example.study_music.com.xkh.music.main.MainDelegate;

public class MainActivity extends BaseActivity {

    @Override
    public BaseMVPDelegate setRootDelegate() {
        return new MainDelegate();
    }

}
