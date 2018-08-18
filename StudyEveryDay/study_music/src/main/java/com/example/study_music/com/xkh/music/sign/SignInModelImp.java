package com.example.study_music.com.xkh.music.sign;

import com.example.study_core.rx.RxJava2Manager;
import com.example.study_core.util.log.StudyLogger;
import com.example.study_music.com.xkh.music.bean.User;
import com.example.study_music.com.xkh.music.http.DefaultObserver;
import com.example.study_music.com.xkh.music.http.HttpUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SignInModelImp implements SignInContract.SignInModel {
    @Override
    public void signIn(String id, String password, final LoginMethod loginMethod, final ISignListener listener) {

        HashMap<String, String> info = new HashMap<>();
        info.put(id, password);

        Disposable disposable = HttpUtils.getApiService(HttpUtils.SERVER_TYPE.ME).singIn(info, loginMethod.name())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultObserver<User>() {

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onSignInFail();
                        }
                    }

                    @Override
                    protected void onNextT(User data) {
                        StudyLogger.e("xiakehe", data.toString());
                        if (listener != null) {
                            SignHandler.SignIn(data, listener);
                        }
                    }

                    @Override
                    protected void onErrorCodeMessage(int code, String message) {
                        SignHandler.SignInFail(message, code, listener);
                        StudyLogger.e("xiakehe", "code is:" + code + "message is:" + message);
                    }
                });

        RxJava2Manager.getRxJava2ManagerInstance().add(disposable);

    }
}
