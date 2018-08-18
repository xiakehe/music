package com.example.study_music.com.xkh.music.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.newmvp.BaseMVPDelegate;
import com.example.study_core.delegate.buttom.BottomItemDelegate;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.main.index.IndexDelegate;
import com.example.study_music.com.xkh.music.main.list.MusicListOnLineDelegate;
import com.example.study_music.com.xkh.music.main.personal.PersonalDelegate;
import com.example.study_music.com.xkh.music.main.video.VideoDelegate;
import com.example.study_music.com.xkh.music.player.ui.MediaBrowserDelegate;
import com.example.study_music.com.xkh.music.view.BottomNavigationViewHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainDelegate extends MediaBrowserDelegate implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected final ArrayList<BaseMVPDelegate> ITEM_DELEGATES = new ArrayList<>();
    protected int mCurrentDelegate = 0;

    private int lastIndex = 0;

    @BindView(R2.id.bottom_bar)
    BottomNavigationView mBottomBar = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_botton;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NotNull View rootView) {
        super.onBindView(savedInstanceState, rootView);
        ITEM_DELEGATES.clear();
        ITEM_DELEGATES.addAll(addItemsDelegate());
        mBottomBar.inflateMenu(R.menu.menu_nav);
        mBottomBar.setOnNavigationItemSelectedListener(this);
        mBottomBar.setItemTextColor(getResources().getColorStateList(R.color.navigation_menu_item_color));
        mBottomBar.setItemIconTintList(getResources().getColorStateList(R.color.navigation_menu_item_color));
        BottomNavigationViewHelper.disableShiftMode(mBottomBar);

        final ISupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new ISupportFragment[ITEM_DELEGATES.size()]);
        getSupportDelegate().loadMultipleRootFragment(R.id.delegate_container, mCurrentDelegate, delegateArray);
        mBottomBar.setSelectedItemId(0);
        Log.d("MainDelegate", "onBindView ");
    }


    protected Collection<? extends BottomItemDelegate> addItemsDelegate() {
        ArrayList<BottomItemDelegate> bottomItemDelegateArrayList = new ArrayList<>();
        bottomItemDelegateArrayList.clear();
        bottomItemDelegateArrayList.add(new IndexDelegate());
        bottomItemDelegateArrayList.add(new MusicListOnLineDelegate());
        bottomItemDelegateArrayList.add(new VideoDelegate());
        bottomItemDelegateArrayList.add(new PersonalDelegate());
        return bottomItemDelegateArrayList;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (lastIndex == i) {
            return true;
        }
        if (i == R.id.navigation_listen) {
            mCurrentDelegate = 0;

        } else if (i == R.id.navigation_music) {
            mCurrentDelegate = 1;

        } else if (i == R.id.navigation_video) {
            mCurrentDelegate = 2;

        } else {
            mCurrentDelegate = 3;
        }
        showHideFragmentByIndex(mCurrentDelegate, lastIndex);
        return true;
    }


    private void showHideFragmentByIndex(int show, int hide) {
        Log.d("MainDelegate", "show is " + show + "  hide is " + hide);
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(show), ITEM_DELEGATES.get(hide));
        lastIndex = show;
    }

    @Override
    protected void hidePlayBack() {

    }

    @Override
    protected void showPlayBack() {

    }

    @Override
    protected void onConnected(MediaBrowserCompat browserCompat) {

    }
}
