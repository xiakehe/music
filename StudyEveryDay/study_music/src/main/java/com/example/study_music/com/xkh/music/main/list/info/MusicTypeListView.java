package com.example.study_music.com.xkh.music.main.list.info;


import com.example.newmvp.MVPDelegate;
import com.example.study_music.com.xkh.music.main.index.bean.SheetInfo;


public interface MusicTypeListView extends MVPDelegate {

    void loadMusicTypeInfo(SheetInfo sheetInfo);
}
