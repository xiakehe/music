package com.example.study_ui.recycler;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.study_ui.GlideUtil;
import com.example.study_ui.R;

import java.util.List;

public abstract class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder> {




    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public MultipleRecyclerAdapter(DataConverter converter) {
        this(converter.covert());
        init();
    }

    protected abstract void init();


    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    protected void loadImage(String img, int viewId, MultipleViewHolder viewHolder) {
        Glide.with(mContext)
                .load(img)
                .apply(GlideUtil.REQUEST_OPTIONS)
                .into((ImageView) viewHolder.getView(viewId));
    }

    protected void loadImage(int drawable, int viewId, MultipleViewHolder viewHolder) {
        Glide.with(mContext)
                .load(drawable)
                .into((ImageView) viewHolder.getView(viewId));

    }

}
