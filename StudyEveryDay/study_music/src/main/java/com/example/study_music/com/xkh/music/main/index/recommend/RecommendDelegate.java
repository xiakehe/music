package com.example.study_music.com.xkh.music.main.index.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.newmvp.MVPDelegatePresenter;
import com.example.study_core.delegate.BaseDelegate;
import com.example.study_core.delegate.web.route.RouteKeys;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.main.index.album.AlbumInfoDelegate;
import com.example.study_music.com.xkh.music.main.index.bean.IndexBean;
import com.example.study_music.com.xkh.music.main.web.WebActivity;
import com.example.study_music.com.xkh.music.player.ui.MediaBrowserDelegate;
import com.example.study_music.com.xkh.music.util.Contact;
import com.example.study_ui.recycler.BaseDecoration;
import com.example.study_ui.recycler.MultipleFields;
import com.example.study_ui.recycler.MultipleItemEntity;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

public class RecommendDelegate extends MediaBrowserDelegate implements RecommendView {


    @BindView(R2.id.rv_recommend)
    RecyclerView mMainRecommendListView = null;
    @BindView(R2.id.in_loading)
    LinearLayout loadingLayout = null;
    @BindView(R2.id.vs_loading_error)
    ViewStub loadingErrorLayout = null;
    private final RecommendDelegatePresenter presenter = new RecommendDelegatePresenter(this);

    @Override
    protected void onConnected(MediaBrowserCompat browserCompat) {
    }

    @Override
    protected void hidePlayBack() {

    }

    @Override
    protected void showPlayBack() {

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_recommend;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        presenter.getRecommendMultipleItemEntity();
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 6);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mMainRecommendListView.setLayoutManager(manager);
        mMainRecommendListView.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.list_line_bg_color), 2));
    }

    @Override
    public void onBindView(@org.jetbrains.annotations.Nullable Bundle savedInstanceState, @NotNull View rootView) {
        super.onBindView(savedInstanceState, rootView);
        initRecyclerView();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public MVPDelegatePresenter<?>[] createPresenters() {
        MVPDelegatePresenter<?>[] delegatePresenters = new MVPDelegatePresenter[1];
        delegatePresenters[0] = presenter;

        return delegatePresenters;
    }

    @NotNull
    @Override
    public BaseDelegate getHostDelegate() {
        return this;
    }

    @Override
    public void startLoading() {
        showView(loadingLayout, true);
    }

    @Override
    public void onLoadSuccess() {
        showView(loadingLayout, false);
    }

    @Override
    public void onLoadError(@NotNull String error) {
        loadingErrorLayout.setVisibility(View.VISIBLE);
        showView(loadingLayout, false);
    }

    private class RecommendDelegatePresenter extends MVPDelegatePresenter<RecommendView> {

        RecommendDelegatePresenter(@org.jetbrains.annotations.Nullable RecommendView view) {
            super(view);
        }

        void getRecommendMultipleItemEntity() {
            RecommendModelImp.getRecommendData(new RecommendListener() {
                @Override
                public void startLoading() {
                    getView().startLoading();
                }

                @Override
                public void onLoad(ArrayList<MultipleItemEntity> list) {
                    getView().load(list);
                }

                @Override
                public void loadError(String e) {
                    getView().onLoadError(e);
                }

                @Override
                public void loadSuccess() {
                    getView().onLoadSuccess();
                }
            });
        }
    }


    @Override
    public void load(final ArrayList<MultipleItemEntity> list) {
        if (list.size() > 0) {
            RecommendAdapter recommendAdapter = RecommendAdapter.create(list, getContext());
            recommendAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            recommendAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                    if (view.getId() == R.id.iv_only_one_image) {
                        IndexBean.ResultBeanXXXXXXXXXXXXXX.Mod29Bean mod29Bean = (IndexBean.ResultBeanXXXXXXXXXXXXXX.Mod29Bean) list.get(position).getFields(MultipleFields.BEAN);
                        String url = mod29Bean.getResult().get(0).getType_id();
                        String title = mod29Bean.getResult().get(0).getTitle();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra(RouteKeys.URL.name(), url);
                        intent.putExtra(RouteKeys.TITLE.name(), title);
                        startActivity(intent);


                    }

                    if (view.getId() == R.id.ll_recommend_good_music_item) {
                        getParentDelegate().getParentDelegate().start(new AlbumInfoDelegate());
                    }
                    if (view.getId() == R.id.iv_image_gird_item) {
                        MultipleItemEntity entity = list.get(position);
                        if (TextUtils.equals((String) entity.getFields(MultipleFields.SORT_TITLE), Contact.MUSIC_ACTIVITY)) {
                            String url = (String) entity.getFields(MultipleFields.KEY);
                            String title = (String) entity.getFields(MultipleFields.TITLE);
                            Intent intent = new Intent(getContext(), WebActivity.class);
                            intent.putExtra(RouteKeys.URL.name(), url);
                            intent.putExtra(RouteKeys.TITLE.name(), title);
                            startActivity(intent);
                        }
                    }


                }
            });
            mMainRecommendListView.setAdapter(recommendAdapter);
            showView(loadingLayout, false);
            recommendAdapter.notifyDataSetChanged();
        }
    }
}
