package com.example.study_music.com.xkh.music.sign;

public interface ISignListener {

    void onSignInSuccess();
    void onSignInFail();
    void onSignInFailMessage(String msg,int code);
}
