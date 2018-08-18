package com.example.study_music.com.xkh.music.main.list;


import android.content.Context;

import com.example.newmvp.MVPDelegatePresenter;
import com.example.study_core.app.Study;
import com.example.study_music.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class MusicOnlinePresenterImp extends MVPDelegatePresenter<MusicListOnLineDelegateContract.MusicOnLineListView> {


    private MusicOnlineModelImp modelImp;
    private Context mContext;

    MusicOnlinePresenterImp(@Nullable MusicListOnLineDelegateContract.MusicOnLineListView view, MusicOnlineModelImp onlineModelImp, Context context) {
        super(view);
        modelImp = onlineModelImp;
        this.mContext = context;
    }


    void loadOnLineMusicSheetListInfo() {
        if (mContext == null) {
            mContext = Study.getApplicationContext();
        }
        String[] titles = mContext.getResources().getStringArray(R.array.online_music_list_title);
        String[] types = mContext.getResources().getStringArray(R.array.online_music_list_type);
        modelImp.loadOnLineMusicSheetList(titles, types, new MusicOnlineListener() {
            @Override
            public void onLoadData(ArrayList<SheetInfoSection> list) {
                getView().loadData(list);
                getView().onLoadSuccess();
            }

            @Override
            public void onStart() {
                getView().startLoading();
            }

            @Override
            public void onError(String e) {
                getView().onLoadError(e);
            }
        });
    }
}
