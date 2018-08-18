package com.example.study_music.com.xkh.music.main.personal;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.study_music.R;

import java.util.List;

public class PersonalSectionAdapter extends BaseSectionQuickAdapter<PersonalSection, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public PersonalSectionAdapter(int layoutResId, int sectionHeadResId, List<PersonalSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, PersonalSection item) {
        if (item.header != null) {
            helper.setText(R.id.tv_only_one, item.header);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalSection item) {
        helper.setImageResource(R.id.iv_mine_title_icon_item, item.t.getResId());
        helper.setText(R.id.tv_name_item, item.t.getTitle());
        helper.setVisible(R.id.iv_mine_right_icon_item, item.t.isHasMore());
    }
}
