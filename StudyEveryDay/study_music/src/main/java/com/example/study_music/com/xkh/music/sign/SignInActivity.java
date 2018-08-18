package com.example.study_music.com.xkh.music.sign;


import com.example.newmvp.BaseMVPDelegate;
import com.example.study_music.com.xkh.music.main.RootDelegateBaseActivity;

public class SignInActivity extends RootDelegateBaseActivity {


    @Override
    public BaseMVPDelegate setRootDelegate() {
        return new SignInDelegate();
    }
}
