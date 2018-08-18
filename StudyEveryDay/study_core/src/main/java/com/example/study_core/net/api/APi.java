package com.example.study_core.net.api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public final class APi {


    private final int READ_TIME_OUT;
    private final int CONNECT_TIME_OUT;
    private OkHttpClient okHttpClient;
    private static final long CACHE_STALE_SEC = 24 * 60 * 60 * 2;
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    private static final String CACHE_CONTROL_AGE = "max-age=0";
    private static final long CACHE_SIZE = 1024 * 1024 * 200;
    private final File HTTP_CACHE;


    public APi(int read_time_out, int connect_time_out, File http_cache) {
        READ_TIME_OUT = read_time_out;
        CONNECT_TIME_OUT = connect_time_out;
        HTTP_CACHE = http_cache;
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .cache(new Cache(HTTP_CACHE, CACHE_SIZE))
                .build();
    }
}
