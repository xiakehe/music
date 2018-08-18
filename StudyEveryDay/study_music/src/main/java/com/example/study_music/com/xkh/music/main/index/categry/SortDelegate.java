package com.example.study_music.com.xkh.music.main.index.categry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.newmvp.BaseMVPDelegate;
import com.example.study_core.net.RestClient;
import com.example.study_core.net.callback.IError;
import com.example.study_core.net.callback.IFailure;
import com.example.study_core.net.callback.ISuccess;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.util.Contact;
import com.example.study_ui.recycler.BaseDecoration;

import butterknife.BindView;

public class SortDelegate extends BaseMVPDelegate {


    @BindView(R2.id.rv_sort)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_categry;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }

    private void initData() {
        RestClient.RestClientBuilder().withUrl(Contact.CATE)
                .withSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        SortDataCover dataCover = new SortDataCover();
                        dataCover.setJsonData(response);
                        SortAdapter adapter = SortAdapter.create(dataCover);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .withError(new IError() {
                    @Override
                    public void onError(int errorCode, String msg) {

                    }
                })
                .withFailure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build()
                .get();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(),6);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.light_gray), 1));
//        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
//        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

}
