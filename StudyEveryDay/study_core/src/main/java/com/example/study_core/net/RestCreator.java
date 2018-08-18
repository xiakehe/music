package com.example.study_core.net;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.example.study_core.app.ConfigKeys;
import com.example.study_core.app.Study;
import com.example.study_core.util.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class RestCreator {
    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getWeakHashMap() {
        return ParamsHolder.PARAMS;
    }

    private static final ArrayList<Interceptor> INTERCEPTORS = Study.getConfiguration(ConfigKeys.INTERCEPTOR);


    /**
     * okHttp搭建
     */
    private static final class OKHttpHolder {

        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }};


        private static OkHttpClient.Builder getOkHttpClient() {
            if (INTERCEPTORS != null) {
                int size = INTERCEPTORS.size();
                for (int i = 0; i < size; i++) {
                    BUILDER.addInterceptor(INTERCEPTORS.get(i));
                }
            }

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new SecureRandom());
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }

            SSLSocketFactory sslSocketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
            BUILDER.sslSocketFactory(sslSocketFactory, new HttpsUtil.UnSafeTrustManager());
            BUILDER.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            BUILDER.addNetworkInterceptor(mRewriteCacheControlInterceptor);
            File cache = new File(Study.getApplicationContext().getCacheDir(), "cache");
            Cache httpCache = new Cache(cache, 1024 * 1024 * 100);
            BUILDER.cache(httpCache);
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = getOkHttpClient().
                connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

    }

    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String cacheControl = request.cacheControl().toString();
            if (!NetworkUtil.isNetConnected(Study.getApplicationContext())) {
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        .addHeader("Cache-Control", CACHE_CONTROL_CACHE)
                        .build();
            } else {
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        .addHeader("Cache-Control", CACHE_CONTROL_AGE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetworkUtil.isNetConnected(Study.getApplicationContext())) {

                return response.newBuilder().addHeader("Cache-Control", cacheControl).build();
            } else {
                return response.newBuilder().addHeader("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
            }
        }
    };


    /**
     * 构建全局Retrofit客户端
     */

    public static final class RetrofitHolder {
        private static final String BASE_URL = Study.getApiHost();
        private final static String API_TING = "https://tingapi.ting.baidu.com/";
        private static final Gson gson = new GsonBuilder().serializeNulls().disableInnerClassSerialization()
                .disableHtmlEscaping().create();

        public static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        public static final Retrofit RETROFIT_CLIENT_BAI_DU = new Retrofit.Builder()
                .baseUrl(API_TING)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * Service接口
     */
    private static final class RetrofitServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService getRestService() {


        return RetrofitServiceHolder.REST_SERVICE;
    }


}
