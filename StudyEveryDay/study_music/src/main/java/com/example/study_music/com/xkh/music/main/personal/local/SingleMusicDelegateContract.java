package com.example.study_music.com.xkh.music.main.personal.local;

import com.example.newmvp.MVPDelegate;
import com.example.study_music.com.xkh.music.bean.Music;

import java.util.ArrayList;

public interface SingleMusicDelegateContract {
    interface SingleMusicDelegateView extends MVPDelegate {
        void showEmpty();
        void showMusics(ArrayList<Music> musics);
        void showMusicInfo(Music music);
    }

    interface SingleMusicModel {
        void scanMusic(ScanMusicListener scanMusicListener);

        void deleteMusic(Music music);
    }

}
