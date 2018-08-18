package com.example.study_core.net;

import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RestService {

    @GET
    Call<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @POST
    @FormUrlEncoded
    Call<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @PUT
    @FormUrlEncoded
    Call<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

}
