package com.example.xiakehe.studyeveryday.app;

import android.app.Application;

import com.example.study_core.app.Study;
import com.example.study_core.net.interceptor.DebugInterceptor;
import com.example.study_core.util.ScreenUtils;
import com.example.study_music.com.xkh.music.util.CoverLoader;
import com.example.xiakehe.studyeveryday.R;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class StudyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Study.appInit(this)
                .withApiHost("http://192.168.1.7")
                .withIcon(new FontAwesomeModule())
                .withInterceptors(new HttpHeadInterceptor())
                .configDone();
        ScreenUtils.init(this,this);
        CoverLoader.get().init(this);
    }

    private class HttpHeadInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            builder.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");
//            if (CheckNetwork.isNetworkConnected(context)) {
//                int maxAge = 60;
//                builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
//            } else {
//                int maxStale = 60 * 60 * 24 * 28;
//                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
//            }
            // 可添加token
//            if (listener != null) {
//                builder.addHeader("token", listener.getToken());
//            }
            // 如有需要，添加请求头
//            builder.addHeader("a", HttpHead.getHeader(request.method()));
            return chain.proceed(builder.build());
        }
    }

}
