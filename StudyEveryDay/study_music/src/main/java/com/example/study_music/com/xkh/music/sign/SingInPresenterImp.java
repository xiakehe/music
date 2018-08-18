package com.example.study_music.com.xkh.music.sign;

import org.jetbrains.annotations.Nullable;

public class SingInPresenterImp extends SignInContract.SignInPresenter implements ISignListener {


    public SingInPresenterImp(@Nullable SignInContract.SignInView view, SignInContract.SignInModel model) {
        super(view, model);
    }

    @Override
    void signIn(String id, String password, LoginMethod loginMethod) {
        this.model.signIn(id, password, loginMethod, this);
    }

    @Override
    public void onSignInSuccess() {
        getView().onSignInSuccess();
    }

    @Override
    public void onSignInFail() {
        getView().onLoadError("无法连接到服务器，登录失败");
    }

    @Override
    public void onSignInFailMessage(String msg, int code) {
        getView().onSignInFailMessage(msg, code);
    }
}
