package com.example.study_music.com.xkh.music.main.index.recommend;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.study_core.delegate.web.route.RouteKeys;
import com.example.study_music.R;
import com.example.study_music.com.xkh.music.main.index.bean.IndexBean;
import com.example.study_music.com.xkh.music.main.index.recommend.banner.BannerViewCreater;
import com.example.study_music.com.xkh.music.main.web.WebActivity;
import com.example.study_music.com.xkh.music.util.Contact;
import com.example.study_ui.recycler.ItemType;
import com.example.study_ui.recycler.MultipleFields;
import com.example.study_ui.recycler.MultipleItemEntity;
import com.example.study_ui.recycler.MultipleRecyclerAdapter;
import com.example.study_ui.recycler.MultipleViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends MultipleRecyclerAdapter implements BaseQuickAdapter.SpanSizeLookup {
    private Context mContext;

    private RecommendAdapter(List<MultipleItemEntity> list, Context context) {
        super(list);
        this.mContext = context;
    }

    @Override
    protected void init() {
        addItemType(ItemType.BANNER, R.layout.recommend_banner);
        addItemType(ItemType.NAV, R.layout.recommend_header);
        addItemType(ItemType.HOT, R.layout.adapter_recommend_two_item);
        addItemType(ItemType.TITLE, R.layout.adapter_recommend_head_item);
        addItemType(ItemType.GOOD_MUSIC, R.layout.adapter_recommend_one_item);
        addItemType(ItemType.NEW, R.layout.adapter_recommend_gird_item_one);
        addItemType(ItemType.WALL_FU_FEI_ACTIVITY, R.layout.adapter_recommend_gird_item);
        addItemType(ItemType.AD, R.layout.adapter_recommend_one_image_item);
        addItemType(ItemType.TODAY, R.layout.adapter_recommend_two_item);
        addItemType(ItemType.UB, R.layout.adapter_recommend_one_image_item);
        setSpanSizeLookup(this);
    }


    public static RecommendAdapter create(List<MultipleItemEntity> list, Context context) {

        return new RecommendAdapter(list, context);

    }

    @Override
    protected void convert(MultipleViewHolder helper, final MultipleItemEntity item) {

        String imageUrl;
        final String title;
        String sub_title;


        switch (helper.getItemViewType()) {

            case ItemType.TITLE:
                title = (String) item.getFields(MultipleFields.TITLE);
                helper.setText(R.id.title_text, title);
                break;
            case ItemType.BANNER:
                ConvenientBanner<String> mHomeBanner = helper.getView(R.id.home_banner);
                ArrayList<String> imagesList = new ArrayList<>();
                IndexBean.ResultBeanXXXXXXXXXXXXXX.FocusBean focusBean = (IndexBean.ResultBeanXXXXXXXXXXXXXX.FocusBean) item.getFields(MultipleFields.BEAN);
                if (focusBean == null) {
                    break;
                }
                final ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX> resultBean = (ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX>) focusBean.getResult();
                for (IndexBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX beanX :
                        resultBean) {
                    imagesList.add(beanX.getRandpic());
                }
                mHomeBanner.setPages(new BannerViewCreater(), imagesList)
                        .setPageIndicator(new int[]{R.drawable.ic_dot_normal, R.drawable.ic_dot_seleted})
                        .setCanLoop(true);
                mHomeBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(RouteKeys.URL.name(), resultBean.get(position).getCode());
                        mContext.startActivity(intent);
                    }
                });
                break;


            case ItemType.HOT:

                IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix2Bean resultBeanXX = (IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix2Bean) item.getFields(MultipleFields.BEAN);
                if (resultBeanXX != null && resultBeanXX.getResult() != null && resultBeanXX.getResult().size() > 0) {
                    IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix2Bean.Mix2BeanResultBeanXX resultBeanXX1 = resultBeanXX.getResult().get(0);
                    imageUrl = resultBeanXX1.getPic();
                    title = resultBeanXX1.getTitle();
                    sub_title = resultBeanXX1.getDesc();
                    loadImage(imageUrl, R.id.left_image_pic, helper);
                    helper.setText(R.id.tv_right_text_title, title);
                    helper.setText(R.id.tv_right_text_subTitle, sub_title);
                } else {
                    helper.setVisible(R.id.rl_recommend_hot_item, false);
                    helper.setVisible(R.id.left_image_pic, false);
                    helper.setVisible(R.id.tv_right_text_title, false);
                    helper.setVisible(R.id.tv_right_text_subTitle, false);
                }

                break;

            case ItemType.GOOD_MUSIC:

                title = (String) item.getFields(MultipleFields.TITLE);
                imageUrl = (String) item.getFields(MultipleFields.IMAGE);
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(imageUrl)) {
                    helper.setVisible(R.id.ll_recommend_good_music_item, false);
                } else {
                    helper.setVisible(R.id.ll_recommend_good_music_item, true);
                    helper.addOnClickListener(R.id.ll_recommend_good_music_item);
                    loadImage(imageUrl, R.id.iv_one_image, helper);
                    helper.setText(R.id.tv_one_text, title);
                }

                break;

            case ItemType.NEW:
                title = (String) item.getFields(MultipleFields.TITLE);
                sub_title = (String) item.getFields(MultipleFields.SUB_TITLE);
                imageUrl = (String) item.getFields(MultipleFields.IMAGE);
                loadImage(imageUrl, R.id.iv_image_two_item, helper);
                helper.setText(R.id.tv_title_two_item, title);
                helper.setText(R.id.tv_sub_title_two_item, sub_title);
                break;

            case ItemType.WALL_FU_FEI_ACTIVITY:

                helper.addOnClickListener(R.id.iv_image_gird_item);
                title = (String) item.getFields(MultipleFields.TITLE);
                imageUrl = (String) item.getFields(MultipleFields.IMAGE);
                loadImage(imageUrl, R.id.iv_image_gird_item, helper);
                helper.setText(R.id.tv_title_gird_item, title);
                if (TextUtils.equals((String) item.getFields(MultipleFields.SORT_TITLE), Contact.MUSIC_ACTIVITY)) {
                    helper.getView(R.id.iv_image_gird_item).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String url = (String) item.getFields(MultipleFields.KEY);
                            Intent intent = new Intent(mContext, WebActivity.class);
                            intent.putExtra(RouteKeys.URL.name(), url);
                            intent.putExtra(RouteKeys.TITLE.name(), title);
                            mContext.startActivity(intent);
                        }
                    });
                }

                break;
            case ItemType.UB:
            case ItemType.AD:
                IndexBean.ResultBeanXXXXXXXXXXXXXX.Mod29Bean mod29Bean = (IndexBean.ResultBeanXXXXXXXXXXXXXX.Mod29Bean) item.getFields(MultipleFields.BEAN);
                if (mod29Bean != null) {
                    imageUrl = mod29Bean.getResult().get(0).getPic();
                    loadImage(imageUrl, R.id.iv_only_one_image, helper);
                    helper.addOnClickListener(R.id.iv_only_one_image);
                }


                break;
            case ItemType.TODAY:
                title = (String) item.getFields(MultipleFields.TITLE);
                sub_title = (String) item.getFields(MultipleFields.SUB_TITLE);
                imageUrl = (String) item.getFields(MultipleFields.IMAGE);

                loadImage(imageUrl, R.id.left_image_pic, helper);
                helper.setText(R.id.tv_right_text_title, title);
                helper.setText(R.id.tv_right_text_subTitle, sub_title);
                break;

            default:
                break;
        }

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {

        MultipleItemEntity entity = getItem(position);
        if (entity != null) {
            return (int) entity.getFields(MultipleFields.SPAN);
        }
        return 0;
    }
}
