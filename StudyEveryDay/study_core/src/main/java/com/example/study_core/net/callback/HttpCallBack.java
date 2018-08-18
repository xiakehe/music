package com.example.study_core.net.callback;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpCallBack implements Callback<String> {

    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final IRequest REQUEST;

    public HttpCallBack(ISuccess success, IError error, IFailure failure, IRequest request) {
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.REQUEST = request;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {

        if (response.isSuccessful()) {
            if (call.isExecuted()){
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }

        } else if (ERROR != null) {
            ERROR.onError(response.code(), response.message());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {


        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

    }
}
