package com.example.study_music.com.xkh.music.main.index.categry;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.study_music.R;
import com.example.study_ui.recycler.DataConverter;
import com.example.study_ui.recycler.ItemType;
import com.example.study_ui.recycler.MultipleFields;
import com.example.study_ui.recycler.MultipleItemEntity;
import com.example.study_ui.recycler.MultipleRecyclerAdapter;
import com.example.study_ui.recycler.MultipleViewHolder;


public class SortAdapter extends MultipleRecyclerAdapter implements BaseQuickAdapter.SpanSizeLookup {

    private SortAdapter(DataConverter converter) {
        super(converter);
    }

    public static SortAdapter create(DataConverter dataConverter) {
        return new SortAdapter(dataConverter);
    }

    @Override
    protected void init() {
        addItemType(ItemType.SORT_NAME, R.layout.adapter_sort_one_title_item);
        addItemType(ItemType.SORT_INFO, R.layout.adapter_sort_item);
        setSpanSizeLookup(this);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {

        switch (helper.getItemViewType()) {
            case ItemType.SORT_NAME:
                String name = (String) item.getFields(MultipleFields.SORT_NAME);
                if (!TextUtils.isEmpty(name)) {
                    helper.setText(R.id.sort_one_title, name);
                }

                break;
            case ItemType.SORT_INFO:
                String title = (String) item.getFields(MultipleFields.SORT_TITLE);
                if (!TextUtils.isEmpty(title)) {
                    helper.setText(R.id.sort_item_text, title);
                }
                String imageUrl = (String) item.getFields(MultipleFields.SORT_ICON);
                if (!TextUtils.isEmpty(imageUrl)) {
                    loadImage(imageUrl, R.id.sort_item_image, helper);
                }


                break;
            default:
                break;
        }

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return (int) getData().get(position).getFields(MultipleFields.SORT_SPAN);
    }
}
