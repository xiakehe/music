package com.example.study_music.com.xkh.music.player.model;

public interface ILoadResult<T> {

    void onLoadResult(T t);

    void onLoadSuccess();

    void onLoadError(String e);

}
