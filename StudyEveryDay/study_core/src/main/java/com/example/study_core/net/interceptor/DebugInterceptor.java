package com.example.study_core.net.interceptor;

import android.support.annotation.RawRes;

import com.example.study_core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;
    private final int RAW_ID;

    public DebugInterceptor(String DEBUG_URL, int RAW_ID) {
        this.DEBUG_URL = DEBUG_URL;
        this.RAW_ID = RAW_ID;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {

        String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, RAW_ID);
        }

        return chain.proceed(chain.request());
    }

    private Response getResponse(Chain chain, String json) {
        return new Response.Builder().code(200)
                .protocol(Protocol.HTTP_1_1)
                .message("ok")
                .request(chain.request())
                .addHeader("Content_Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .build();
    }

    private Response debugResponse(Chain chain, @RawRes int resId) {
        String json = FileUtil.getRawFile(resId);
        return getResponse(chain, json);
    }
}
