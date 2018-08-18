package com.example.study_music.com.xkh.music.main.personal.local;

import android.content.Context;
import android.util.Log;

import com.example.study_core.rx.RxJava2Manager;
import com.example.study_core.util.file.ACache;
import com.example.study_music.com.xkh.music.bean.Music;
import com.example.study_music.com.xkh.music.util.Contact;
import com.example.study_music.com.xkh.music.util.MusicUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SingleMusicModelImp implements SingleMusicDelegateContract.SingleMusicModel {

    private Context mContext;

    public SingleMusicModelImp(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void scanMusic(final ScanMusicListener scanMusicListener) {


        Observable<ArrayList<Music>> cacheData = Observable.create(new ObservableOnSubscribe<ArrayList<Music>>() {
            @SuppressWarnings("unchecked")
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Music>> emitter) throws Exception {
                ArrayList<Music> list = (ArrayList<Music>) ACache.get(mContext).getAsObject(Contact.LOCAL_MUSIC);
                if (list != null) {
                    emitter.onNext(list);
                }
                emitter.onComplete();
            }
        });


        Observable<ArrayList<Music>> scanMusicObservable = Observable.create(new ObservableOnSubscribe<ArrayList<Music>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Music>> emitter) throws Exception {
                ArrayList<Music> list;
                list = MusicUtils.scanMusic(mContext);
                if (list == null) {
                    emitter.onError(new Throwable("music list is null"));
                } else {
                    emitter.onNext(list);
                    emitter.onComplete();
                }

            }
        });
        Disposable disposable = Observable.concat(cacheData, scanMusicObservable).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Music>>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onNext(ArrayList<Music> music) {
                        scanMusicListener.onScanMusicResult(music);
                        ACache.get(mContext).put(Contact.LOCAL_MUSIC, music);
                    }

                    @Override
                    public void onError(Throwable e) {
                        scanMusicListener.onError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        scanMusicListener.onSuccess();
                    }
                });

        RxJava2Manager.getRxJava2ManagerInstance().add(disposable);


    }

    @Override
    public void deleteMusic(Music music) {

    }
}
