package com.example.study_core.net;

import com.example.study_core.net.callback.HttpCallBack;
import com.example.study_core.net.callback.IError;
import com.example.study_core.net.callback.IFailure;
import com.example.study_core.net.callback.IRequest;
import com.example.study_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public final class RestClient {
    private final static WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private final String URL;
    private final RequestBody BODY;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;


    RestClient(String url, WeakHashMap<String, Object> params, RequestBody body, IRequest request, ISuccess success, IFailure failure, IError error) {
        this.URL = url;
        this.BODY = body;
        REQUEST = request;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        PARAMS.putAll(params);
    }

    public static RestClientBuilder RestClientBuilder() {
        return new RestClientBuilder();
    }

    private void request(Method method) {
        final RestService restService = RestCreator.getRestService();
        Call<String> call = null;

        switch (method) {
            case GET:
                call = restService.get(URL, PARAMS);
                break;
            case POST:
                call = restService.post(URL, PARAMS);
                break;
            case PUT:
                call = restService.put(URL, PARAMS);
                break;
            case DELETE:
                call = restService.delete(URL, PARAMS);
                break;
            case PUT_RAW:
                call = restService.putRaw(URL, BODY);
                break;
            case POST_RAW:
                call = restService.postRaw(URL, BODY);
            default:
                break;

        }

        if (call != null) {
            if (REQUEST != null) {
                REQUEST.onRequestStart();
            }
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new HttpCallBack(SUCCESS, ERROR, FAILURE, REQUEST);
    }

    public final void get() {
        request(Method.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(Method.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("post body PARAMS must is Empty");
            }
            request(Method.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(Method.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("put body PARAMS must is Empty");
            }
            request(Method.PUT_RAW);
        }
    }

    public final void delete() {
        request(Method.DELETE);
    }


}


