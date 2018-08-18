package com.example.study_music.com.xkh.music.main.list;



import com.example.study_core.app.Study;
import com.example.study_core.rx.RxJava2Manager;
import com.example.study_core.util.file.ACache;
import com.example.study_music.com.xkh.music.http.HttpUtils;
import com.example.study_music.com.xkh.music.main.index.bean.OnlineMusicList;
import com.example.study_music.com.xkh.music.main.index.bean.SheetInfo;
import com.example.study_music.com.xkh.music.util.Contact;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MusicOnlineModelImp {
    private final ArrayList<SheetInfoSection> sheetInfoSections = new ArrayList<>();

    public void loadOnLineMusicSheetList(final String[] titles, final String[] types, final MusicOnlineListener listener) {

        Observable<SheetInfoSection> cache = Observable.create(new ObservableOnSubscribe<SheetInfoSection>() {
            @SuppressWarnings("unchecked")
            @Override
            public void subscribe(ObservableEmitter<SheetInfoSection> emitter) {
                List<SheetInfoSection> list = (List<SheetInfoSection>) ACache.get(Study.getApplicationContext()).getAsObject(Contact.ONLINE_MUSIC_CACHE);
                if (list == null || list.size() <= 0) {
                    emitter.onComplete();
                } else {
                    sheetInfoSections.addAll(list);
                    emitter.onComplete();
                }
            }
        });


        Observable<SheetInfo> sheetInfoObservable = Observable.create(new ObservableOnSubscribe<SheetInfo>() {
            @Override
            public void subscribe(ObservableEmitter<SheetInfo> emitter) {

                if (sheetInfoSections.size() > 0) {

                    emitter.onComplete();
                } else {
                    int size = titles.length;

                    for (int i = 0; i < size; i++) {
                        SheetInfo sheetInfo = new SheetInfo();
                        sheetInfo.setTitle(titles[i]);
                        sheetInfo.setType(types[i]);
                        emitter.onNext(sheetInfo);
                    }

                    emitter.onComplete();
                }
            }
        });


        Observable<OnlineMusicList> onlineMusicListObservable = sheetInfoObservable.
                flatMap(new Function<SheetInfo, Observable<OnlineMusicList>>() {
                    @Override
                    public Observable<OnlineMusicList> apply(final SheetInfo sheetInfo) {

                        if (!sheetInfo.getType().equals("#")) {
                            LinkedHashMap<String, String> params = new LinkedHashMap<>();
                            params.put(Contact.PARAM_TYPE, sheetInfo.getType());
                            params.put(Contact.PARAM_METHOD, Contact.METHOD_GET_MUSIC_LIST);
                            params.put(Contact.PARAM_OFFSET, "0");
                            params.put(Contact.PARAM_SIZE, "3");
                            return HttpUtils.getApiService(HttpUtils.SERVER_TYPE.BAI_DU).getOnlineMusicList(params);
                        }

                        return Observable.create(new ObservableOnSubscribe<OnlineMusicList>() {
                            @Override
                            public void subscribe(ObservableEmitter<OnlineMusicList> emitter) {

                                emitter.onNext(new OnlineMusicList());

                            }
                        });
                    }
                });


        Observable<SheetInfoSection> network = Observable.zip(sheetInfoObservable, onlineMusicListObservable, new BiFunction<SheetInfo, OnlineMusicList, SheetInfoSection>() {

            @Override
            public SheetInfoSection apply(SheetInfo sheetInfo, OnlineMusicList onlineMusicList) {

                SheetInfoSection builder;
                if (sheetInfo.getType().equals("#")) {
                    builder = new SheetInfoSection(true, sheetInfo.getTitle());

                } else {
                    ArrayList<OnlineMusicList.OnlineMusic> onlineMusics = (ArrayList<OnlineMusicList.OnlineMusic>) onlineMusicList.getSong_list();

                    SheetInfo temp = new SheetInfo();
                    temp.setType(sheetInfo.getType());
                    temp.setTitle(sheetInfo.getTitle());
                    temp.setUrl(onlineMusicList.getBillboard().getPic_s260());

                    if (onlineMusics.size() >= 1) {
                        temp.setMusicOne(onlineMusics.get(0).getTitle());
                        temp.setMusicOneAu(onlineMusics.get(0).getArtist_name());
                    }
                    if (onlineMusics.size() >= 2) {
                        temp.setMusicTwo(onlineMusics.get(1).getTitle());
                        temp.setMusicTwoAu(onlineMusics.get(1).getArtist_name());

                    }
                    if (onlineMusics.size() >= 3) {
                        temp.setMusicThree(onlineMusics.get(2).getTitle());
                        temp.setMusicThreeAu(onlineMusics.get(2).getArtist_name());
                    }
                    builder = new SheetInfoSection(temp);

                }

                return builder;
            }
        });
        Disposable disposable = Observable.concat(cache, network).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SheetInfoSection>() {
                    @Override
                    protected void onStart() {
                        listener.onStart();
                    }

                    @Override
                    public void onNext(SheetInfoSection list) {
                        sheetInfoSections.add(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        ACache.get(Study.getApplicationContext()).put(Contact.ONLINE_MUSIC_CACHE, sheetInfoSections, Contact.LOCAL_MUSIC_CACHE_TIME);
                        listener.onLoadData(sheetInfoSections);
                    }
                });

        RxJava2Manager.getRxJava2ManagerInstance().add(disposable);


    }
}
