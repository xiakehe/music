package com.example.study_music.com.xkh.music.http;

import com.google.gson.annotations.SerializedName;

public class DefaultBean<T> {

    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
