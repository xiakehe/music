package com.example.study_music.com.xkh.music.main.personal.local;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newmvp.BaseMVPDelegate;
import com.example.newmvp.MVPDelegatePresenter;
import com.example.study_core.delegate.BaseDelegate;
import com.example.study_core.util.ToastHelp;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.bean.Music;
import com.example.study_music.com.xkh.music.main.personal.PersonItem;
import com.example.study_music.com.xkh.music.main.personal.PersonalSection;
import com.example.study_music.com.xkh.music.main.personal.PersonalSectionAdapter;
import com.example.study_ui.recycler.BaseDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

public class SingleMusicDelegate extends BaseMVPDelegate implements SingleMusicDelegateContract.SingleMusicDelegateView {
    @BindView(R2.id.rv_single_music)
    RecyclerView mSingleMusics;
    SingleMusicAdapter musicAdapter = null;

    private SingleMusicPresenterImp presenterImp = new SingleMusicPresenterImp(this, this, new SingleMusicModelImp(getContext()));

    @org.jetbrains.annotations.Nullable
    @Override
    public MVPDelegatePresenter<?>[] createPresenters() {
        return super.createPresenters();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        presenterImp.scanMusic();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_local_single_music;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initViews();
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSingleMusics.setLayoutManager(layoutManager);
        mSingleMusics.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.line_write_bg_color), 2));

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showMusics(final ArrayList<Music> musics) {
        if (musicAdapter == null) {
            musicAdapter = new SingleMusicAdapter(R.layout.adapter_local_single_music_item, musics);
            mSingleMusics.setAdapter(musicAdapter);
            musicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                    initBottomSheetDialog(musics.get(position));

                }
            });
        }
        musicAdapter.notifyDataSetChanged();
    }


    private void initBottomSheetDialog(Music music) {
        ArrayList<PersonalSection> personalSections = new ArrayList<>();
        personalSections.clear();
        String title = music.getTitle();
        String art = music.getArtist();
        String album = music.getAlbum();
        int[] resId = new int[]{
                0,
                R.drawable.ic_next_music, R.drawable.ic_collect, R.drawable.ic_comment, R.drawable.ic_share,
                R.drawable.ic_upload_cloud, R.drawable.ic_singer, R.drawable.ic_music_disc, R.drawable.ic_video,
                R.drawable.ic_ring_tone, R.drawable.ic_music_info,
                R.drawable.ic_delete
        };

        final String[] titles = new String[]{
                "歌曲：" + title,
                "下一首播放", "收藏到歌单", "评论",
                "分享", "上传到云盘", "歌手：" + art, "专辑：" + album, "查看视频",
                "设为铃声", "查看歌曲详细信息", "删除"

        };
        int i = titles.length;
        for (int j = 0; j < i; j++) {
            if (j == 0) {
                PersonalSection section = new PersonalSection(true, titles[j]);
                personalSections.add(section);
            } else {
                PersonalSection section = new PersonalSection(new PersonItem(resId[j], titles[j]));
                personalSections.add(section);
            }
        }
        View root = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_dialog_view, null);
        BottomSheetDialog sheetDialog = new BottomSheetDialog(mContext);
        sheetDialog.setContentView(root);
        RecyclerView recyclerView = root.findViewById(R.id.rv_local_music_menu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        PersonalSectionAdapter adapter = new PersonalSectionAdapter(R.layout.adapter_mine_list_item, R.layout.only_one_text, personalSections);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.mine_list_item_view_bg_color), 2));
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastHelp.show(mContext, titles[position]);
            }
        });
        sheetDialog.show();


    }

    @Override
    public void showMusicInfo(Music music) {

    }

    @NotNull
    @Override
    public BaseDelegate getHostDelegate() {
        return this;
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void onLoadError(@NotNull String error) {

    }
}
