package com.example.study_ui.recycler;

import android.text.TextUtils;

import java.util.ArrayList;

public abstract class DataConverter {

    protected ArrayList<MultipleItemEntity> DATAS = new ArrayList<>();

    private String mJsonData = null;

    public void setJsonData(String mJsonData) {
        this.mJsonData = mJsonData;
    }

    public String getJsonData() {
        if (TextUtils.isEmpty(mJsonData)) {
            throw new NullPointerException("json data is null");
        }

        return mJsonData;
    }

    public abstract ArrayList<MultipleItemEntity> covert();
}
