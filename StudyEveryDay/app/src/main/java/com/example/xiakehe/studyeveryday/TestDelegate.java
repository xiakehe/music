package com.example.xiakehe.studyeveryday;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.study_core.app.AccountManager;
import com.example.study_core.delegate.PermissionDelegate;
import com.example.study_core.net.RestClient;
import com.example.study_core.net.callback.IError;
import com.example.study_core.net.callback.IFailure;
import com.example.study_core.net.callback.IRequest;
import com.example.study_core.net.callback.ISuccess;
import com.example.study_core.util.storage.StudySharedPreference;
import com.example.study_ui.luancher.ScrollLauncherTag;
import com.example.study_ui.recycler.DataConverter;

import butterknife.OnClick;

public class TestDelegate extends PermissionDelegate {
    private static final String TAG = "TestDelegate";



    @Override
    public Object setLayout() {
        return R.layout.content_main;
    }

    @OnClick(R.id.clear_first_start_app)
    void onClickClearFirstStartApp(){
        StudySharedPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),false);
    }

    @OnClick(R.id.clear_login_state)
    void onClickClearLoginState(){
        AccountManager.setSingState(false);
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View rootView) {
//        RestClient restClient = RestClient.RestClientBuilder().withUrl("http://tingapi.ting.baidu.com/v1/restserver/ting?size=2&type=2&_t=1468380543284&format=json&method=baidu.ting.billboard.billList")
//
//                .withSuccess(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        DataConverter recommendDataCover=new RecommendDataCover();
//                        recommendDataCover.setJsonData(response);
//                        Log.d(TAG, recommendDataCover.getJsonData());
//                        Toast.makeText(TestDelegate.this.getContext(), response, Toast.LENGTH_LONG).show();
//                    }
//                }).withFailure(new IFailure() {
//                    @Override
//                    public void onFailure() {
//                        Log.d(TAG, "onFailure");
//
//                        Toast.makeText(TestDelegate.this.getContext(), "onFailure", Toast.LENGTH_LONG).show();
//                    }
//                }).withRequest(new IRequest() {
//                    @Override
//                    public void onRequestStart() {
//                        Log.d(TAG, "onRequestStart");
//
//                        Toast.makeText(TestDelegate.this.getContext(), "onRequestStart", Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onRequestEnd() {
//                        Log.d(TAG, "onRequestEnd");
//
//                        Toast.makeText(TestDelegate.this.getContext(), "onRequestEnd", Toast.LENGTH_LONG).show();
//
//                    }
//                }).withError(new IError() {
//                    @Override
//                    public void onError(int errorCode, String msg) {
//                        Log.d(TAG, "IError");
//                        Log.d(TAG, "IError"+errorCode+msg);
//
//
//                        Toast.makeText(TestDelegate.this.getContext(), "errorCode" + errorCode, Toast.LENGTH_LONG).show();
//
//                    }
//                }).build();
//        restClient.post();
    }

}
