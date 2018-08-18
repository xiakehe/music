package com.example.study_ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

public class MultipleItemEntity implements MultiItemEntity {

    private LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();

    private SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE = new SoftReference<>(MULTIPLE_FIELDS, ITEM_QUEUE);


    @Override
    public int getItemType() {
        if (FIELDS_REFERENCE!=null){
            return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
        }
        return 0;

    }

    public MultipleItemEntity(LinkedHashMap<Object, Object> map) {
        FIELDS_REFERENCE.get().putAll(map);
    }

    public void setFields(Object key, Object value) {
        FIELDS_REFERENCE.get().put(key, value);
    }

    public Object getFields(Object key) {
        return FIELDS_REFERENCE.get().get(key);
    }

    public static MultipleEntityBuilder Builder() {
        return new MultipleEntityBuilder();
    }

    @Override
    public String toString() {
        return FIELDS_REFERENCE.toString();
    }
}
