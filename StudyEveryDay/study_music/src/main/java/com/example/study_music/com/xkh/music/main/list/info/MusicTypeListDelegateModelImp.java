package com.example.study_music.com.xkh.music.main.list.info;


import com.example.study_core.rx.RxJava2Manager;
import com.example.study_music.com.xkh.music.http.HttpUtils;
import com.example.study_music.com.xkh.music.main.index.bean.OnlineMusicList;
import com.example.study_music.com.xkh.music.main.index.bean.SheetInfo;
import com.example.study_music.com.xkh.music.util.Contact;


import java.util.LinkedHashMap;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MusicTypeListDelegateModelImp {


    private SheetInfo sheetInfo = new SheetInfo();


    @SuppressWarnings("unchecked")
    public void requestMusicByType(String type, final String offset, String size, final MusicTypeListListener listListener) {

        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put(Contact.PARAM_TYPE, type);
        params.put(Contact.PARAM_METHOD, Contact.METHOD_GET_MUSIC_LIST);
        params.put(Contact.PARAM_OFFSET, offset);
        params.put(Contact.PARAM_SIZE, size);

        Observable<OnlineMusicList> onlineMusicListObservable = HttpUtils.getApiService(HttpUtils.SERVER_TYPE.BAI_DU).getOnlineMusicList(params);
        Disposable ds = onlineMusicListObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new DisposableObserver<OnlineMusicList>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onNext(OnlineMusicList onlineMusicList) {
                        OnlineMusicList.Billboard billboard = onlineMusicList.getBillboard();
                        sheetInfo.setName(billboard.getName());
                        sheetInfo.setUrl(billboard.getPic_s260());
                        sheetInfo.setUpdate(billboard.getUpdate_date());
                        sheetInfo.setInfo(billboard.getComment());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listListener!=null){
                            listListener.onError(e.toString());
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (listListener != null) {
                            listListener.onLoadTypeInfo(sheetInfo);
                        }

                    }
                });

        RxJava2Manager.getRxJava2ManagerInstance().add(ds);
    }
}
