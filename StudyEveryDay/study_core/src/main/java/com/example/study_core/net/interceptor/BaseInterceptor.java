package com.example.study_core.net.interceptor;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public abstract class BaseInterceptor implements Interceptor {

    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        final HttpUrl httpUrl = chain.request().url();
        int size = httpUrl.pathSize();
        for (int i = 0; i < size; i++) {
            params.put(httpUrl.queryParameterName(i), httpUrl.queryParameterValue(i));
        }
        return params;
    }

    protected String getUrlParameters(Chain chain, String key) {
        Request request = chain.request();
        return request.url().queryParameter(key);
    }

    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {

        FormBody formBody = (FormBody) chain.request().body();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        int size = 0;
        if (formBody != null) {
            size = formBody.size();
        }
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }

    protected String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }


}
