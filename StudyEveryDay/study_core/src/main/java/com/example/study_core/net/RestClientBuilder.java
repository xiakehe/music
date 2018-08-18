package com.example.study_core.net;

import com.example.study_core.net.callback.IError;
import com.example.study_core.net.callback.IFailure;
import com.example.study_core.net.callback.IRequest;
import com.example.study_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.RequestBody;

public final class RestClientBuilder {

    private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private String mUrl = null;
    private RequestBody mBody;
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IError mError;
    private IFailure mFailure;


    RestClientBuilder() {
    }

    public final RestClientBuilder withUrl(String url) {
        mUrl = url;
        return this;
    }

    public final RestClientBuilder withRequestBody(RequestBody body) {
        this.mBody = body;
        return this;
    }

    public final RestClientBuilder withParams(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder withParams(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder withSuccess(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final RestClientBuilder withRequest(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder withError(IError error) {
        this.mError = error;
        return this;
    }
    public final RestClientBuilder withFailure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mBody, mRequest, mSuccess, mFailure, mError);
    }


}
