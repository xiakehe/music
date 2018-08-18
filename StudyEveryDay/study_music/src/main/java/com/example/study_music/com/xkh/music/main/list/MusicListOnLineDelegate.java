package com.example.study_music.com.xkh.music.main.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newmvp.MVPDelegatePresenter;
import com.example.study_core.delegate.BaseDelegate;
import com.example.study_core.delegate.buttom.BottomItemDelegate;
import com.example.study_core.util.ToastHelp;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.main.BaseActivity;
import com.example.study_music.com.xkh.music.main.PlaybackControlsFragment;
import com.example.study_music.com.xkh.music.main.list.info.MusicTypeListDelegate;
import com.example.study_music.com.xkh.music.view.MusicToolBar;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

public class MusicListOnLineDelegate extends BottomItemDelegate implements MusicListOnLineDelegateContract.MusicOnLineListView {

    @BindView(R2.id.rl_music_list_on_line)
    RecyclerView mMusicOnlineView;
    private MusicOnlineAdapter adapter = null;

    @BindView(R2.id.ll_loading)
    LinearLayout ll_loading;

    @BindView(R2.id.tool_bar_layout)
    MusicToolBar toolBar = null;
    private ArrayList<SheetInfoSection> data = new ArrayList<>();
    private MusicOnlinePresenterImp presenterImp = new MusicOnlinePresenterImp(this, new MusicOnlineModelImp(), getContext());

    protected BaseActivity _mActivity = null;
    private PlaybackControlsFragment controlsFragment = null;


    @org.jetbrains.annotations.Nullable
    @Override
    public MVPDelegatePresenter<?>[] createPresenters() {
        MVPDelegatePresenter<?>[] delegatePresenters = new MVPDelegatePresenter[1];
        delegatePresenters[0] = presenterImp;
        return delegatePresenters;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("MusicListOnLineDelegate", "onStart :" + shouldShowControls());

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (shouldShowControls()) {
            showControls();
        }
    }


    protected boolean shouldShowControls() {
        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(getActivity());
        if (mediaController == null ||
                mediaController.getMetadata() == null ||
                mediaController.getPlaybackState() == null) {
            return false;
        }
        switch (mediaController.getPlaybackState().getState()) {
            case PlaybackStateCompat.STATE_ERROR:
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                return false;
            default:
                return true;
        }
    }

    private void showControls() {
        if (controlsFragment == null) {
            controlsFragment = new PlaybackControlsFragment();
            getSupportDelegate().loadRootFragment(R.id.delegate_controls_container, controlsFragment);
        }

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_music_list_online;
    }

    @Override
    public void onBindView(@org.jetbrains.annotations.Nullable Bundle savedInstanceState, @NotNull View rootView) {
        super.onBindView(savedInstanceState, rootView);
        initRecycleView();
        toolBar.setVisibility(View.GONE);
    }

    private void initRecycleView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMusicOnlineView.setLayoutManager(layoutManager);

        adapter = new MusicOnlineAdapter(R.layout.adapter_music_list_sheet_item, R.layout.adapter_music_list_sheet_profile_item, data);
        mMusicOnlineView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SheetInfoSection section = data.get(position);
                if (!section.isHeader) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", section.t.getTitle());
                    bundle.putString("type", section.t.getType());
                    MusicTypeListDelegate typeListDelegate = new MusicTypeListDelegate();
                    typeListDelegate.setArguments(bundle);
                    getParentDelegate().start(typeListDelegate);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (presenterImp != null) {
            presenterImp.loadOnLineMusicSheetListInfo();
        }
    }

    @Override
    public void loadData(ArrayList<SheetInfoSection> data) {
        if (adapter != null && data.size() > 0) {
            adapter.replaceData(data);
        }
    }

    @NotNull
    @Override
    public BaseDelegate getHostDelegate() {
        return this;
    }

    @Override
    public void startLoading() {
        ll_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadSuccess() {
        ll_loading.setVisibility(View.GONE);
    }

    @Override
    public void onLoadError(@NotNull String error) {
        ToastHelp.show(mContext, error);
    }

}
