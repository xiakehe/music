package com.example.study_music.com.xkh.music.main.list.info;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newmvp.MVPDelegatePresenter;
import com.example.study_core.delegate.BaseDelegate;
import com.example.study_core.util.ToastHelp;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.main.index.bean.SheetInfo;
import com.example.study_music.com.xkh.music.player.MusicService;
import com.example.study_music.com.xkh.music.player.ui.MediaBrowserDelegate;
import com.example.study_music.com.xkh.music.util.Contact;
import com.example.study_music.com.xkh.music.view.MusicLoadMoreView;
import com.example.study_music.com.xkh.music.view.MusicToolBar;
import com.example.study_music.com.xkh.music.view.ToolBarType;
import com.example.study_ui.recycler.BaseDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MusicTypeListDelegate extends MediaBrowserDelegate implements MusicTypeListView, BaseQuickAdapter.RequestLoadMoreListener, MusicToolBar.ToolBarNormalSearchClickListener {

    @BindView(R2.id.rl_music_list_on_line)
    RecyclerView mMusicOnlineView;

    @BindView(R2.id.ll_loading)
    LinearLayout ll_loading;

    @BindView(R2.id.tool_bar_layout)
    MusicToolBar toolBar = null;

    private String title = null;
    private String type = null;
    private MusicTypeAdapter musicTypeAdapter = null;
    private List<MediaBrowserCompat.MediaItem> list = new ArrayList<>();
    public static final String TAG = "MusicTypeListDelegate";

    private int loadCount = 0;
    private int loadSize = 10;
    private static final int MAX_SIZE = 100;
    private MusicTypeListDelegatePresenterImp presenterImp = new MusicTypeListDelegatePresenterImp(this);
    private MediaBrowserCompat.SubscriptionCallback subscriptionCallback = new MediaBrowserCompat.SubscriptionCallback() {

        @Override
        public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children, @NonNull Bundle options) {
            super.onChildrenLoaded(parentId, children, options);
            Log.d(TAG, "loadMusicListData musicList+" + children.size());
            if (children.size() <= 0) {
                musicTypeAdapter.loadMoreFail();
            }

            loadCount += children.size();
            if (loadCount > MAX_SIZE) {
                musicTypeAdapter.loadMoreEnd();
            }
            list.addAll(children);
            musicTypeAdapter.loadMoreComplete();
        }

        @Override
        public void onError(@NonNull String parentId) {
            super.onError(parentId);
        }
    };

    @org.jetbrains.annotations.Nullable
    @Override
    public MVPDelegatePresenter<?>[] createPresenters() {
        MVPDelegatePresenter<?>[] mvpDelegatePresenters = new MVPDelegatePresenter[1];
        mvpDelegatePresenters[0] = presenterImp;
        return mvpDelegatePresenters;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecycleView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMusicOnlineView.setLayoutManager(layoutManager);
        musicTypeAdapter = new MusicTypeAdapter(R.layout.adapter_local_single_music_item, list);
        mMusicOnlineView.setAdapter(musicTypeAdapter);
        mMusicOnlineView.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.line_write_bg_color), 2));

        musicTypeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        musicTypeAdapter.setEnableLoadMore(true);
        musicTypeAdapter.setOnLoadMoreListener(this, mMusicOnlineView);
        musicTypeAdapter.setLoadMoreView(new MusicLoadMoreView());

        musicTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MediaControllerCompat.getMediaController(getActivity()).getTransportControls()
                        .playFromMediaId(list.get(position).getDescription().getMediaId(), null);
                mediaBrowserProvider.onMediaItemSelected(list.get(position));
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        presenterImp.requestMusicByType(type, String.valueOf(loadCount), String.valueOf(loadSize));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        type = getArguments().getString("type");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_music_list_online;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecycleView();
        toolBar.setCenterText(title);
        toolBar.reInitViewByMode(ToolBarType.NORMAL, false);
        showView(ll_loading, false);

    }

    @Override
    public void onStart() {
        toolBar.setmNormalSearchClickListener(this);
        super.onStart();
    }

    @Override
    protected void hidePlayBack() {

    }

    @Override
    protected void showPlayBack() {

    }

    @Override
    protected void onConnected(MediaBrowserCompat browserCompat) {

        Bundle bundle = new Bundle();
        bundle.putString(Contact.PARAM_TYPE, type);
        bundle.putString(Contact.PARAM_OFFSET, String.valueOf(loadCount));
        bundle.putString(Contact.PARAM_SIZE, String.valueOf(loadSize));
        browserCompat.subscribe(MusicService.MEDIA_LIST, bundle, subscriptionCallback);

    }

    private void initHeader(SheetInfo info) {
        @SuppressLint("InflateParams") View vHeader = LayoutInflater.from(mContext).inflate(R.layout.delegate_music_type_header_view, null);
        final ImageView ivCover = vHeader.findViewById(R.id.iv_cover);
        final ImageView headBg = vHeader.findViewById(R.id.iv_bg_header);

        final TextView tvTitle = vHeader.findViewById(R.id.tv_title);
        final TextView tvUpdateDate = vHeader.findViewById(R.id.tv_update_date);
        final TextView tvComment = vHeader.findViewById(R.id.tv_comment);
        tvTitle.setText(info.getName());
        tvUpdateDate.setText(getString(R.string.recent_update, info.getUpdate()));
        tvComment.setText(info.getInfo());
        Glide.with(mContext)
                .asBitmap().load(info.getUrl())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ivCover.setImageBitmap(resource);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@NonNull Palette palette) {
                                Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                                if (swatch != null) {
                                    int titleColor = swatch.getTitleTextColor();
                                    int bodyColor = swatch.getBodyTextColor();
                                    tvTitle.setTextColor(titleColor);
                                    tvComment.setTextColor(bodyColor);
                                    tvUpdateDate.setTextColor(bodyColor);

                                }
                                int bgColor = palette.getVibrantColor(getResources().getColor(R.color.theme_red));
                                headBg.setBackgroundColor(bgColor);
                            }
                        });
                    }
                });
        if (musicTypeAdapter != null) {
            musicTypeAdapter.addHeaderView(vHeader);
            musicTypeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void loadMusicTypeInfo(SheetInfo info) {
        initHeader(info);
    }

    @Override
    public void onLoadMoreRequested() {

        postRunnable(new Runnable() {
            @Override
            public void run() {

                Bundle bundle = new Bundle();
                bundle.putString(Contact.PARAM_TYPE, type);
                bundle.putString(Contact.PARAM_OFFSET, String.valueOf(loadCount));
                bundle.putString(Contact.PARAM_SIZE, String.valueOf(loadSize));
                if (browserCompat != null) {
                    browserCompat.unsubscribe(MusicService.MEDIA_LIST);
                    browserCompat.subscribe(MusicService.MEDIA_LIST, bundle, subscriptionCallback);
                }

            }
        });

    }

    @Override
    public void onReturn() {
        getSupportDelegate().pop();
    }

    @Override
    public void onClickSearch() {
        ToastHelp.show(mContext, "search");
    }

    @NotNull
    @Override
    public BaseDelegate getHostDelegate() {
        return this;
    }

    @Override
    public void startLoading() {
        ToastHelp.show(mContext, "startLoading");
        showView(ll_loading, true);
    }

    @Override
    public void onLoadSuccess() {
        ToastHelp.show(mContext, "onLoadSuccess");
        showView(ll_loading, false);
        musicTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadError(@NotNull String error) {
        ToastHelp.show(mContext, "error:" + error);
    }
}
