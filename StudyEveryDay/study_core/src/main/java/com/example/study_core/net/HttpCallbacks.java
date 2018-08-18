package com.example.study_core.net;

/**
 * Created by hzwangchenyan on 2017/2/8.
 */
public abstract class HttpCallbacks<T> {
    public abstract void onSuccess(T t);

    public abstract void onFail(Exception e);

    public void onFinish() {
    }
}
