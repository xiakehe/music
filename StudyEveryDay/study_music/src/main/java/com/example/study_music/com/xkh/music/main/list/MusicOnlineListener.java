package com.example.study_music.com.xkh.music.main.list;


import java.util.ArrayList;

public interface MusicOnlineListener {

    void onLoadData(ArrayList<SheetInfoSection> list);
    void onStart();
    void onError(String e);

}
