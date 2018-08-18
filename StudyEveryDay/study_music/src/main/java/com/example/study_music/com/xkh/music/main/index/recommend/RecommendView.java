package com.example.study_music.com.xkh.music.main.index.recommend;

import com.example.newmvp.MVPDelegate;
import com.example.study_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public interface RecommendView extends MVPDelegate {

    void load(ArrayList<MultipleItemEntity> list);
}
