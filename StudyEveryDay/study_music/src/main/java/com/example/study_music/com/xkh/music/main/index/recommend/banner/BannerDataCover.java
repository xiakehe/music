package com.example.study_music.com.xkh.music.main.index.recommend.banner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.study_ui.recycler.DataConverter;
import com.example.study_ui.recycler.MultipleEntityBuilder;
import com.example.study_ui.recycler.MultipleFields;
import com.example.study_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class BannerDataCover extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> covert() {

        JSONObject result = JSON.parseObject(getJsonData()).getJSONObject("result");
        JSONObject focus = result.getJSONObject("focus");
        JSONArray resultArray = focus.getJSONArray("result");
        final int size = resultArray.size();
        String image, code;
        MultipleEntityBuilder builder = new MultipleEntityBuilder();
        for (int i = 0; i < size; i++) {
            JSONObject object = resultArray.getJSONObject(i);
            image = object.getString("randpic");
            code = object.getString("code");
            builder.setField(MultipleFields.IMAGE, image);
            builder.setField(MultipleFields.BANNERS, code);
            MultipleItemEntity entity = builder.build();
            DATAS.add(entity);

        }

        return DATAS;
    }
}
