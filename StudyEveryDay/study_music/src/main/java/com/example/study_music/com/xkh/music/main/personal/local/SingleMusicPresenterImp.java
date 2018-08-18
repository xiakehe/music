package com.example.study_music.com.xkh.music.main.personal.local;


import android.Manifest;

import com.example.newmvp.MVPDelegatePresenter;
import com.example.study_core.util.PermissionReq;
import com.example.study_music.R;
import com.example.study_music.com.xkh.music.bean.Music;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SingleMusicPresenterImp extends MVPDelegatePresenter<SingleMusicDelegateContract.SingleMusicDelegateView> implements ScanMusicListener {
    private SingleMusicModelImp singleMusicModel;
    private SingleMusicDelegate delegate;

    public SingleMusicPresenterImp(SingleMusicDelegate delegate, @Nullable SingleMusicDelegateContract.SingleMusicDelegateView view, SingleMusicModelImp musicModelImp) {
        super(view);
        this.singleMusicModel = musicModelImp;
        this.delegate = delegate;
    }

    @Override
    public void onScanMusicResult(ArrayList<Music> musicList) {
        if (musicList.size() > 0) {
            getView().showMusics(musicList);
        } else {
            getView().showEmpty();
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(String e) {

    }

    void scanMusic() {

        PermissionReq.with(delegate).permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).result(new PermissionReq.Result() {
            @Override
            public void onGranted() {
                singleMusicModel.scanMusic(new ScanMusicListener() {
                    @Override
                    public void onScanMusicResult(ArrayList<Music> musicList) {

                        getView().showMusics(musicList);
                    }

                    @Override
                    public void onSuccess() {
                        getView().onLoadSuccess();
                    }

                    @Override
                    public void onError(String e) {
                        getView().onLoadError(e);
                    }
                });
            }

            @Override
            public void onDenied() {

                getView().showEmpty();
                getView().onLoadError(delegate.getContext().getString(R.string.no_storage_permission));
            }
        }).request();
    }


    void shareMusic(Music music) {

    }

    void setMusicTone(Music musicTone) {

    }

    void seeMusicInfo(Music music) {
    }

}
