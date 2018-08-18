package com.example.study_music.com.xkh.music.http;


import io.reactivex.observers.DisposableObserver;

public abstract class DefaultObserver<T> extends DisposableObserver<DefaultBean<T>> {

    @SuppressWarnings("unchecked")
    @Override
    public void onNext(DefaultBean<T> tDefaultBean) {
        int code = tDefaultBean.getCode();
        if (code == 200) {
            onNextT(tDefaultBean.getData());

        } else {
            onErrorCodeMessage(code, tDefaultBean.getMessage());
        }
    }

    protected abstract void onNextT(T data);

    protected abstract void onErrorCodeMessage(int code, String message);
}
