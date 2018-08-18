package com.example.study_music.com.xkh.music.sign;

import com.example.study_core.app.AccountManager;
import com.example.study_core.rx.Event;
import com.example.study_core.rx.RxJava2Manager;
import com.example.study_music.com.xkh.music.bean.User;


public class SignHandler {


    public static void SignIn(User user, ISignListener signListener) {
        RxJava2Manager.getRxJava2ManagerInstance().sendEvent(new Event<>(user, Event.EVENT_INFO.SIGN_IN));
        signListener.onSignInSuccess();
        AccountManager.setSingState(true);


    }

    public static void SignUp(String response, ISignListener signListener) {

    }

    public static void SignInFail(ISignListener signListener) {
        AccountManager.setSingState(false);
        signListener.onSignInFail();

    }

    public static void SignInFail(String error, int code, ISignListener signListener) {

        AccountManager.setSingState(false);
        signListener.onSignInFailMessage(error, code);

    }

    public static void SignUpFail(ISignListener signListener) {

    }

    public static void SignUpFail(String error, int code, ISignListener signListener) {

    }
}
