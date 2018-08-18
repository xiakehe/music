package com.example.study_core.delegate.buttom;

import java.util.LinkedHashMap;

public final class ItemBuilder {

    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();


    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public ItemBuilder addItem(BottomTabBean bottomTabBean, BottomItemDelegate bottomItemDelegate) {
        ITEMS.put(bottomTabBean, bottomItemDelegate);
        return this;
    }

    public ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> itemDelegateLinkedHashMap) {
        ITEMS.putAll(itemDelegateLinkedHashMap);
        return this;
    }

    public LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
