package com.example.study_music.com.xkh.music.player.model;

import com.example.study_core.app.Study;
import com.example.study_core.util.file.ACache;
import com.example.study_music.com.xkh.music.base.BaseLoad;
import com.example.study_music.com.xkh.music.bean.MusicDownloadInfo;
import com.example.study_music.com.xkh.music.http.HttpUtils;
import com.example.study_music.com.xkh.music.util.Contact;

import java.util.LinkedHashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadMusicDownLoadInfo {

    public interface LoadMusicInfo extends BaseLoad {
        void onLoadMusicInfo(MusicDownloadInfo info);
    }

    public static void startDownLoadMusic(String musicId, final LoadMusicInfo mLoader) {
        if (mLoader == null) {
            return;
        }

//        MusicDownloadInfo info = (MusicDownloadInfo) ACache.get(Study.getApplicationContext()).getAsObject(musicId);
//        if (info != null) {
//            mLoader.startLoad();
//            mLoader.onLoadMusicInfo(info);
//            mLoader.loadSuccess();
//            return;
//        }

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put(Contact.PARAM_SONG_ID, musicId);
        params.put(Contact.PARAM_METHOD, Contact.METHOD_DOWNLOAD_MUSIC);
        HttpUtils.getApiService(HttpUtils.SERVER_TYPE.BAI_DU).getOnlineMusicDownLoadInfo(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MusicDownloadInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mLoader.startLoad();
                    }

                    @Override
                    public void onNext(MusicDownloadInfo info) {
                        mLoader.onLoadMusicInfo(info);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoader.loadError();
                    }

                    @Override
                    public void onComplete() {
                        mLoader.loadSuccess();
                    }
                });

    }
}
