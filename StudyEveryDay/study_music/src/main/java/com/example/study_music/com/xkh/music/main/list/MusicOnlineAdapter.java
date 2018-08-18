package com.example.study_music.com.xkh.music.main.list;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.study_music.R;
import com.example.study_ui.GlideUtil;

import java.util.List;

public class MusicOnlineAdapter extends BaseSectionQuickAdapter<SheetInfoSection, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    MusicOnlineAdapter(int layoutResId, int sectionHeadResId, List<SheetInfoSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SheetInfoSection item) {
        String title = item.header;
        if (!TextUtils.isEmpty(title)) {
            helper.setText(R.id.tv_profile, title);
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, SheetInfoSection item) {
        String img = item.t.getUrl();
        String music_one = item.t.getMusicOne();
        String music_au = item.t.getMusicOneAu();
        String music_two_name = item.t.getMusicTwo();
        String music_two_au = item.t.getMusicTwoAu();
        String music_three_name = item.t.getMusicThree();
        String music_three_au = item.t.getMusicThreeAu();
        if (!TextUtils.isEmpty(img)) {
            Glide.with(mContext)
                    .load(img)
                    .apply(GlideUtil.REQUEST_OPTIONS)
                    .into((ImageView) helper.getView(R.id.iv_cover));
        }
        if (!TextUtils.isEmpty(music_one) && !TextUtils.isEmpty(music_au)) {
            helper.setText(R.id.tv_music_1, mContext.getResources().getString(R.string.song_list_item_title_1, music_one, music_au));
        }
        if (!TextUtils.isEmpty(music_two_name) && !TextUtils.isEmpty(music_two_au)) {
            helper.setText(R.id.tv_music_2, mContext.getResources().getString(R.string.song_list_item_title_2, music_two_name, music_two_au));
        }
        if (!TextUtils.isEmpty(music_three_name) && !TextUtils.isEmpty(music_three_au)) {
            helper.setText(R.id.tv_music_3, mContext.getResources().getString(R.string.song_list_item_title_3, music_three_name, music_three_au));
        }

    }
}
