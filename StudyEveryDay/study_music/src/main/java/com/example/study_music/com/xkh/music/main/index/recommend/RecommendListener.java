package com.example.study_music.com.xkh.music.main.index.recommend;

import com.example.study_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public interface RecommendListener {
    void startLoading();

    void onLoad(ArrayList<MultipleItemEntity> list);

    void loadError(String error);

    void loadSuccess();
}
