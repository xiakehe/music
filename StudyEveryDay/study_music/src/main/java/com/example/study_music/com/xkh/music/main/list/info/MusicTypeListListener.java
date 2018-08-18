package com.example.study_music.com.xkh.music.main.list.info;



import com.example.study_music.com.xkh.music.main.index.bean.SheetInfo;



public interface MusicTypeListListener {


    void onError(String e);

    void onLoadTypeInfo(SheetInfo info);
}
