package com.example.study_music.com.xkh.music.main.index.categry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.study_ui.recycler.DataConverter;
import com.example.study_ui.recycler.ItemType;
import com.example.study_ui.recycler.MultipleEntityBuilder;
import com.example.study_ui.recycler.MultipleFields;
import com.example.study_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class SortDataCover extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> covert() {

        JSONObject data = JSON.parseObject(getJsonData()).getJSONObject("data");
        addCate("hotsort", data);
        addCate("allsort", data);
        addCate("moresort", data);
        return DATAS;
    }

    private void addCate(String name, JSONObject object) {

        MultipleEntityBuilder builder = new MultipleEntityBuilder();
        JSONObject cate = object.getJSONObject(name);
        String name2 = cate.getString("name");
        int span = cate.getIntValue("span");
        JSONArray result = cate.getJSONArray("result");
        builder.setField(MultipleFields.SORT_NAME, name2);
        builder.setField(MultipleFields.SORT_SPAN, 6);
        builder.setField(MultipleFields.ITEM_TYPE, ItemType.SORT_NAME);
        MultipleItemEntity entitySortName = builder.build();
        DATAS.add(entitySortName);
        addItem(span, result);

    }


    private void addItem(int span, JSONArray result) {
        int size_temp = result.size();
        for (int k = 0; k < size_temp; k++) {
            JSONObject obj = result.getJSONObject(k);
            String itemName = obj.getString("sortname");
            String itemImage = obj.getString("image");
            MultipleEntityBuilder itemBuider = new MultipleEntityBuilder();
            itemBuider.setField(MultipleFields.SORT_SPAN, span);
            itemBuider.setField(MultipleFields.ITEM_TYPE, ItemType.SORT_INFO);
            itemBuider.setField(MultipleFields.SORT_ICON, itemImage);
            itemBuider.setField(MultipleFields.SORT_TITLE, itemName);
            MultipleItemEntity itemEntity = itemBuider.build();
            DATAS.add(itemEntity);
        }
    }
}
