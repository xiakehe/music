package com.example.study_core.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.newmvp.BaseMVPDelegate;

import java.util.ArrayList;

public abstract class BaseViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tabs;
    private ArrayList<BaseMVPDelegate> studyDelegates;

    public BaseViewPagerAdapter(FragmentManager fm, ArrayList<String> tabs, ArrayList<BaseMVPDelegate> delegates) {

        super(fm);
        this.tabs = tabs;
        this.studyDelegates = delegates;
    }

    @Override
    public Fragment getItem(int position) {
        return studyDelegates.get(position);
    }

    @Override
    public int getCount() {
        return studyDelegates.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }
}
