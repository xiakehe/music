package com.example.study_music.com.xkh.music.main.index.recommend;

import com.example.study_music.com.xkh.music.main.index.bean.IndexBean;
import com.example.study_music.com.xkh.music.util.Contact;
import com.example.study_ui.recycler.ItemType;
import com.example.study_ui.recycler.MultipleEntityBuilder;
import com.example.study_ui.recycler.MultipleFields;
import com.example.study_ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

public class RecommendDataCoverImp {
    private ArrayList<MultipleItemEntity> datas = new ArrayList<>();

    private RecommendDataCoverImp() {
    }

    public static class RecommendDataCoverImpHolder {
        public static RecommendDataCoverImp HOLDER = new RecommendDataCoverImp();
    }


    public ArrayList<MultipleItemEntity> covert(IndexBean indexBean) {

        datas.clear();
        IndexBean.ResultBeanXXXXXXXXXXXXXX rootResult = indexBean.getResult();
        ArrayList<IndexBean.ModuleBean> moduleBeans = (ArrayList<IndexBean.ModuleBean>) indexBean.getModule();
        int size = moduleBeans.size();
        for (int i = 0; i < size; i++) {
            final MultipleEntityBuilder builder = MultipleItemEntity.Builder();
            IndexBean.ModuleBean moduleBean = moduleBeans.get(i);
            String key = moduleBean.getKey();
            String title = moduleBean.getTitle();
            if (key.equals("ad_small") || key.equals("scene")) {
                continue;
            }
            builder.setItemType(moduleBean.getStyle())
                    .setField(MultipleFields.TITLE, title)
                    .setField(MultipleFields.KEY, key)
                    .setField(MultipleFields.SPAN, 6);

            switch (title) {
                case "焦点图":
                    builder.setField(MultipleFields.BEAN, rootResult.getFocus());
                    break;
                case "音乐导航":
                    break;
                case "今日热点":
                    if (rootResult.getMix_2() != null) {
                        builder.setField(MultipleFields.BEAN, rootResult.getMix_2());
                    }
                    break;
                case "精选歌单":
                    addTitle(title);
                    addList(rootResult.getDiy());
                    continue;

                case "广告图片(大)":
                    continue;
                case "U榜":
                    if (rootResult.getMod_29() != null) {
                        builder.setField(MultipleFields.BEAN, rootResult.getMod_29());
                    }
                    break;
                case "新歌首发":
                    addTitle(title);
                    addListMix1(rootResult.getMix_1());
                    continue;
                case Contact.MUSIC_WALL:
                    addTitle(title);
                    addListMix29(rootResult.getMix_29(), title);
                    continue;
                case Contact.FU_FEI_ALBUM:
                    addTitle(title);
                    addListMix22(rootResult.getMix_22(), title);
                    continue;
                case "原商城固定入口（现产品调查问卷）":
                    builder.setField(MultipleFields.IMAGE, rootResult.getMod_27().getResult().get(0).getPic());
                    break;
                case "今日推荐歌曲":
                    addTitle(title);
                    builder.setField(MultipleFields.BEAN, rootResult.getRecsong());
                    addListRecSong(rootResult.getRecsong());

                    continue;
                case Contact.MUSIC_ACTIVITY:
                    addTitle(title);
                    builder.setField(MultipleFields.BEAN, rootResult.getMix_9());
                    addMix9(rootResult.getMix_9(), title);
                    continue;

                case "乐播节目":
                    addTitle(title);
                    addRadio(rootResult.getRadio());
                    continue;

                default:
                    break;

            }

            datas.add(builder.build());

        }


        return datas;
    }

    private void addRadio(IndexBean.ResultBeanXXXXXXXXXXXXXX.RadioBean radio) {
        List<IndexBean.ResultBeanXXXXXXXXXXXXXX.RadioBean.ResultBeanXXXXXXXXX> resultBeanXXXXXXXXXList = radio.getResult();
        if (resultBeanXXXXXXXXXList == null) {
            return;
        }
        int size = resultBeanXXXXXXXXXList.size();
        for (int i = 0; i < size; i++) {
            MultipleEntityBuilder builder = new MultipleEntityBuilder();

            IndexBean.ResultBeanXXXXXXXXXXXXXX.RadioBean.ResultBeanXXXXXXXXX beanXXXXXXX = resultBeanXXXXXXXXXList.get(i);
            builder.setField(MultipleFields.IMAGE, beanXXXXXXX.getPic())
                    .setField(MultipleFields.TITLE, beanXXXXXXX.getTitle())
                    .setField(MultipleFields.SUB_TITLE, beanXXXXXXX.getDesc())
                    .setItemType(ItemType.NEW)
                    .setField(MultipleFields.SPAN, 2);
            datas.add(builder.build());

        }
    }


    private void addMix9(IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix9Bean mix_9, String title) {
        ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix9Bean.ResultBean> resultBeans = (ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix9Bean.ResultBean>) mix_9.getResult();
        int size = resultBeans.size();
        for (int i = 0; i < size; i++) {
            MultipleEntityBuilder builder = new MultipleEntityBuilder();

            IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix9Bean.ResultBean beanXXXXXXX = resultBeans.get(i);
            builder.setField(MultipleFields.IMAGE, beanXXXXXXX.getPic())
                    .setField(MultipleFields.TITLE, beanXXXXXXX.getTitle())
                    .setField(MultipleFields.KEY,beanXXXXXXX.getType_id())
                    .setField(MultipleFields.SORT_TITLE, title)
                    .setItemType(ItemType.WALL_FU_FEI_ACTIVITY)
                    .setField(MultipleFields.SPAN, 2);
            datas.add(builder.build());

        }

    }

    private void addListRecSong(IndexBean.ResultBeanXXXXXXXXXXXXXX.RecsongBean recsong) {
        ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.RecsongBean.ResultBeanXXXXXXXX> resultBeanXXXXXXXXES = (ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.RecsongBean.ResultBeanXXXXXXXX>) recsong.getResult();
        int size = resultBeanXXXXXXXXES.size() > 4 ? 4 : resultBeanXXXXXXXXES.size();
        for (int i = 1; i < size; i++) {
            MultipleEntityBuilder builder = new MultipleEntityBuilder();
            IndexBean.ResultBeanXXXXXXXXXXXXXX.RecsongBean.ResultBeanXXXXXXXX beanXXXXXXX = resultBeanXXXXXXXXES.get(i);
            builder.setField(MultipleFields.IMAGE, beanXXXXXXX.getPic_premium())
                    .setField(MultipleFields.TITLE, beanXXXXXXX.getTitle())
                    .setItemType(ItemType.TODAY)
                    .setField(MultipleFields.SUB_TITLE, beanXXXXXXX.getAuthor())
                    .setField(MultipleFields.SPAN, 6);
            datas.add(builder.build());

        }
    }

    private void addListMix22(IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix22Bean mix_22, String title) {

        List<IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix22Bean.Mix22BeanResultBeanXX> mix_22Result = mix_22.getResult();
        if (mix_22Result == null) {
            return;
        }
        int size = mix_22Result.size();

        for (int i = 0; i < size; i++) {
            MultipleEntityBuilder builder = new MultipleEntityBuilder();

            IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix22Bean.Mix22BeanResultBeanXX beanXXXXXXX = mix_22Result.get(i);
            builder.setField(MultipleFields.IMAGE, beanXXXXXXX.getPic())
                    .setField(MultipleFields.TITLE, beanXXXXXXX.getTitle())
                    .setField(MultipleFields.SORT_TITLE, title)
                    .setField(MultipleFields.SUB_TITLE, beanXXXXXXX.getAuthor())
                    .setItemType(ItemType.WALL_FU_FEI_ACTIVITY)
                    .setField(MultipleFields.SPAN, 2);
            datas.add(builder.build());

        }
    }

    private void addListMix29(IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix29Bean mix_29, String title) {
        ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix29Bean.ResultBean> resultBeans = (ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix29Bean.ResultBean>) mix_29.getResult();
        int size = resultBeans.size();
        for (int i = 0; i < size; i++) {
            MultipleEntityBuilder builder = new MultipleEntityBuilder();

            IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix29Bean.ResultBean beanXXXXXXX = resultBeans.get(i);
            builder.setField(MultipleFields.IMAGE, beanXXXXXXX.getPic())
                    .setField(MultipleFields.SORT_TITLE, title)
                    .setField(MultipleFields.TITLE, beanXXXXXXX.getTitle())
                    .setItemType(ItemType.WALL_FU_FEI_ACTIVITY)
                    .setField(MultipleFields.SPAN, 2);
            datas.add(builder.build());

        }

    }

    private void addListMix1(IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix1Bean mix_1) {
        int size = 0;
        ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix1Bean.ResultBeanXXXXXXX> resultBeanXXXXXXXArrayLis = (ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix1Bean.ResultBeanXXXXXXX>) mix_1.getResult();
        if (resultBeanXXXXXXXArrayLis != null) {
            size = resultBeanXXXXXXXArrayLis.size();

        }
        for (int i = 0; i < size; i++) {
            MultipleEntityBuilder builder = new MultipleEntityBuilder();

            IndexBean.ResultBeanXXXXXXXXXXXXXX.Mix1Bean.ResultBeanXXXXXXX beanXXXXXXX = resultBeanXXXXXXXArrayLis.get(i);
            if (beanXXXXXXX != null) {
                builder.setField(MultipleFields.IMAGE, beanXXXXXXX.getPic())
                        .setField(MultipleFields.TITLE, beanXXXXXXX.getTitle())
                        .setField(MultipleFields.SPAN, 2)
                        .setItemType(ItemType.NEW)
                        .setField(MultipleFields.SUB_TITLE, beanXXXXXXX.getAuthor());
            }

            datas.add(builder.build());

        }
    }

    private void addList(IndexBean.ResultBeanXXXXXXXXXXXXXX.DiyBean diy) {

        ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.DiyBean.ResultBeanXXXXXXXXXXX> list = (ArrayList<IndexBean.ResultBeanXXXXXXXXXXXXXX.DiyBean.ResultBeanXXXXXXXXXXX>) diy.getResult();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            MultipleEntityBuilder builder = new MultipleEntityBuilder();

            IndexBean.ResultBeanXXXXXXXXXXXXXX.DiyBean.ResultBeanXXXXXXXXXXX resultBeanXXXXXXXXXXX = list.get(i);
            builder.setField(MultipleFields.IMAGE, resultBeanXXXXXXXXXXX.getPic())
                    .setField(MultipleFields.TITLE, resultBeanXXXXXXXXXXX.getTitle())
                    .setItemType(ItemType.GOOD_MUSIC)
                    .setField(MultipleFields.SPAN, 2);
            datas.add(builder.build());

        }

    }

    private void addTitle(String title) {
        MultipleEntityBuilder builder = new MultipleEntityBuilder();
        builder.setField(MultipleFields.SPAN, 6);
        builder.setField(MultipleFields.TITLE, title);
        builder.setItemType(ItemType.TITLE);
        datas.add(builder.build());
    }
}
