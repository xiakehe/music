package com.example.study_music.com.xkh.music.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.media.MediaDescriptionCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.study_music.R;
import com.example.study_music.com.xkh.music.player.model.MutableMediaDescriptionMetadata;
import com.example.study_music.com.xkh.music.util.PlayQueueUtils;

import java.util.List;

public class MusicQueueAdapter extends BaseQuickAdapter<MutableMediaDescriptionMetadata,BaseViewHolder> {


    public MusicQueueAdapter(int layoutResId, @Nullable List<MutableMediaDescriptionMetadata> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MutableMediaDescriptionMetadata item) {

        MediaDescriptionCompat desc=item.getMediaDescriptionCompat();

        if (PlayQueueUtils.getInstance().isCurPlayData(desc)) {
            helper.setTextColor(R.id.tv_music_info_queue,mContext.getResources().getColor(R.color.theme_red));
            helper.setTextColor(R.id.tv_music_subtitle_queue,mContext.getResources().getColor(R.color.theme_red));
            helper.setVisible(R.id.iv_is_play,true);
        } else {
            helper.setVisible(R.id.iv_is_play,false);
            helper.setTextColor(R.id.tv_music_info_queue,mContext.getResources().getColor(R.color.black));
            helper.setTextColor(R.id.tv_music_subtitle_queue,mContext.getResources().getColor(R.color.black_50p));
        }
        helper.setText(R.id.tv_music_info_queue,desc.getTitle());
        String artist = "-" + desc.getSubtitle();
        helper.setText(R.id.tv_music_subtitle_queue,artist);
        helper.addOnClickListener(R.id.iv_music_queue_delete);

    }
}
