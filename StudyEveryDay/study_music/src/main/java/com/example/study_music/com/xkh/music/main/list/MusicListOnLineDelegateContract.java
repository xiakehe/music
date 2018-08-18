package com.example.study_music.com.xkh.music.main.list;

import com.example.newmvp.MVPDelegate;

import java.util.ArrayList;

public interface MusicListOnLineDelegateContract {

    interface MusicOnLineListView extends MVPDelegate {
        void loadData(ArrayList<SheetInfoSection> data);
    }
}
