package com.example.study_music.com.xkh.music.main.index.recommend;


import com.example.study_core.app.Study;
import com.example.study_core.rx.RxJava2Manager;
import com.example.study_core.util.file.ACache;
import com.example.study_music.com.xkh.music.http.HttpUtils;
import com.example.study_music.com.xkh.music.main.index.bean.IndexBean;
import com.example.study_music.com.xkh.music.util.Contact;
import com.example.study_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RecommendModelImp {


    @SuppressWarnings("unchecked")
    public static void getRecommendData(final RecommendListener listener) {

        Observable<ArrayList<MultipleItemEntity>> cache = Observable.create(new ObservableOnSubscribe<ArrayList<MultipleItemEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<MultipleItemEntity>> emitter) {
                IndexBean cacheIndexBean = (IndexBean) ACache.get(Study.getApplicationContext())
                        .getAsObject(Contact.RECOMMEND_CACHE);
                if (cacheIndexBean == null || cacheIndexBean.getResult() == null) {
                    emitter.onComplete();
                } else {
                    ArrayList<MultipleItemEntity> cacheData =
                            RecommendDataCoverImp.RecommendDataCoverImpHolder.HOLDER.covert(cacheIndexBean);

                    if (cacheData != null && cacheData.size() > 0) {
                        emitter.onNext(cacheData);
                    }
                    emitter.onComplete();

                }
            }
        });

        Observable<ArrayList<MultipleItemEntity>> network = HttpUtils.getApiService(HttpUtils.SERVER_TYPE.BAI_DU).getIndexBeans()
                .map(new Function<IndexBean, ArrayList<MultipleItemEntity>>() {


                    @Override
                    public ArrayList<MultipleItemEntity> apply(IndexBean indexBean) {

                        ArrayList<MultipleItemEntity> netResult = RecommendDataCoverImp.RecommendDataCoverImpHolder.HOLDER.covert(indexBean);
                        ACache.get(Study.getApplicationContext()).put(Contact.RECOMMEND_CACHE, indexBean, Contact.INDEX_CACHE_TIME);

                        return netResult;
                    }

                });


        Disposable disposable = Observable.concat(cache, network)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new DisposableObserver<ArrayList<MultipleItemEntity>>() {

                    @Override
                    protected void onStart() {
                        listener.startLoading();

                    }


                    @Override
                    public void onNext(ArrayList<MultipleItemEntity> list) {
                        listener.onLoad(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadError(e.toString());

                    }

                    @Override
                    public void onComplete() {
                        listener.loadSuccess();

                    }
                });

        RxJava2Manager.getRxJava2ManagerInstance().add(disposable);
    }
}
