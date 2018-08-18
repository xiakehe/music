package com.example.study_music.com.xkh.music.player.model;

import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;

import com.example.study_core.app.Study;
import com.example.study_core.rx.RxJava2Manager;
import com.example.study_music.R;
import com.example.study_music.com.xkh.music.http.HttpUtils;
import com.example.study_music.com.xkh.music.main.index.bean.Album;
import com.example.study_music.com.xkh.music.main.index.bean.OnlineMusicList;
import com.example.study_music.com.xkh.music.util.Contact;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NetWorkMusicSourceImp implements MusicProviderSource {
    private static List<MediaMetadataCompat> items = new ArrayList<>();

    @Override
    public void requestMusicByType(String type, String offset, String size, final ILoadResult<List<MediaMetadataCompat>> listListener) {
        items.clear();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put(Contact.PARAM_TYPE, type);
        params.put(Contact.PARAM_METHOD, Contact.METHOD_GET_MUSIC_LIST);
        params.put(Contact.PARAM_OFFSET, offset);
        params.put(Contact.PARAM_SIZE, size);

        Observable<OnlineMusicList> onlineMusicListObservable = HttpUtils.getApiService(HttpUtils.SERVER_TYPE.BAI_DU).getOnlineMusicList(params);
        Disposable ds = onlineMusicListObservable.concatMap(new Function<OnlineMusicList, ObservableSource<OnlineMusicList.OnlineMusic>>() {
            @Override
            public ObservableSource<OnlineMusicList.OnlineMusic> apply(OnlineMusicList onlineMusicList) {

                return Observable.fromIterable(onlineMusicList.getSong_list());
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OnlineMusicList.OnlineMusic>() {
                    @Override
                    protected void onStart() {
                        items.clear();
                        super.onStart();
                    }

                    @Override
                    public void onNext(OnlineMusicList.OnlineMusic onlineMusic) {
                        final MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
                        final MediaMetadataCompat.Builder dataBuilder = new MediaMetadataCompat.Builder();

                        builder.setTitle(onlineMusic.getTitle()).setDescription(onlineMusic.getPic_s500())
                                .setSubtitle(onlineMusic.getArtist_name() + "-" + onlineMusic.getAlbum_title())
                                .setMediaId(onlineMusic.getSong_id());

                        dataBuilder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, onlineMusic.getSong_id())
                                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, onlineMusic.getAlbum_title())
                                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, onlineMusic.getArtist_name())
                                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, !TextUtils.isEmpty(onlineMusic.getLanguage()) ? onlineMusic.getLanguage() : Study.getApplicationContext().getResources().getString(R.string.default_type))
                                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, onlineMusic.getPic_big())
                                .putString(MediaMetadataCompat.METADATA_KEY_ART_URI, onlineMusic.getPic_huge())
                                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, onlineMusic.getFile_duration())
                                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, onlineMusic.getTitle());
                        MediaMetadataCompat metadataCompat = dataBuilder.build();
                        items.add(metadataCompat);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listListener.onLoadError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        listListener.onLoadResult(items);
                        listListener.onLoadSuccess();
                    }
                });

        RxJava2Manager.getRxJava2ManagerInstance().add(ds);
    }

    @Override
    public void requestAlbumMusicList(String albumId, ILoadResult<Album> albumILoadResult) {

    }
}
