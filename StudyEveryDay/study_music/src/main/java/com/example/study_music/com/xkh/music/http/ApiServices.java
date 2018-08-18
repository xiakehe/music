package com.example.study_music.com.xkh.music.http;

import com.example.study_music.com.xkh.music.bean.MusicDownloadInfo;
import com.example.study_music.com.xkh.music.bean.User;
import com.example.study_music.com.xkh.music.main.index.bean.IndexBean;
import com.example.study_music.com.xkh.music.main.index.bean.OnlineMusicList;
import com.example.study_music.com.xkh.music.util.Contact;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiServices {

    @GET("v1/restserver/ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14")
    Observable<IndexBean> getIndexBeans();

    @GET("v1/restserver/ting")
    Observable<OnlineMusicList> getOnlineMusicList(@QueryMap Map<String,String> info);

    @GET("v1/restserver/ting")
    Observable<MusicDownloadInfo> getOnlineMusicDownLoadInfo(@QueryMap Map<String,String> info);


    @POST(Contact.SIGN_IN)
    Observable<DefaultBean<User>> singIn(@QueryMap Map<String, String> info, @Header("method") String method);

}
