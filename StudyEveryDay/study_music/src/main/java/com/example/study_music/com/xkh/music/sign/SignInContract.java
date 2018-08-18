package com.example.study_music.com.xkh.music.sign;

import com.example.newmvp.MVPDelegate;
import com.example.newmvp.MVPDelegatePresenter;

import org.jetbrains.annotations.Nullable;

public interface SignInContract {

    interface SignInView extends MVPDelegate {
        void onSignInSuccess();

        void onSignInFailMessage(String msg, int code);
    }

    interface SignInModel {
        void signIn(String id, String password, LoginMethod loginMethod, ISignListener listener);
    }

    abstract class SignInPresenter extends MVPDelegatePresenter<SignInView> {
        SignInModel model;

        public SignInPresenter(@Nullable SignInView view, SignInModel model) {
            super(view);
            this.model = model;
        }

        abstract void signIn(String id, String password, LoginMethod loginMethod);
    }

}
