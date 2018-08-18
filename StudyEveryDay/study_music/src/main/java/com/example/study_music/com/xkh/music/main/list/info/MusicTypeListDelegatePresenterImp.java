package com.example.study_music.com.xkh.music.main.list.info;

import com.example.newmvp.MVPDelegatePresenter;
import com.example.study_music.com.xkh.music.main.index.bean.SheetInfo;

import org.jetbrains.annotations.Nullable;

public class MusicTypeListDelegatePresenterImp extends MVPDelegatePresenter<MusicTypeListView> {

    private MusicTypeListDelegateModelImp modelImp = new MusicTypeListDelegateModelImp();

    MusicTypeListDelegatePresenterImp(@Nullable MusicTypeListView view) {
        super(view);
    }

    public void requestMusicByType(String type, String offset, String size) {

        modelImp.requestMusicByType(type, offset, size, new MusicTypeListListener() {
            @Override
            public void onError(String e) {

                getView().onLoadError(e);
            }

            @Override
            public void onLoadTypeInfo(SheetInfo info) {
                getView().loadMusicTypeInfo(info);
            }
        });
    }
}
