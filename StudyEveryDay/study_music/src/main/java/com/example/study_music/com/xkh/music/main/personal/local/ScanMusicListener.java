package com.example.study_music.com.xkh.music.main.personal.local;

import com.example.study_music.com.xkh.music.bean.Music;

import java.util.ArrayList;

public interface ScanMusicListener {
    void onScanMusicResult(ArrayList<Music> musicList);
    void onSuccess();
    void onError(String e);
}
