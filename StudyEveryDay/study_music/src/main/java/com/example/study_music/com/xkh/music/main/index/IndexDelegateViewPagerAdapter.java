package com.example.study_music.com.xkh.music.main.index;

import android.support.v4.app.FragmentManager;

import com.example.newmvp.BaseMVPDelegate;
import com.example.study_core.adapter.BaseViewPagerAdapter;

import java.util.ArrayList;

public class IndexDelegateViewPagerAdapter extends BaseViewPagerAdapter {


    IndexDelegateViewPagerAdapter(FragmentManager fm, ArrayList<String> tabs, ArrayList<BaseMVPDelegate> delegates) {
        super(fm, tabs, delegates);
    }


    public static class IndexDelegateViewPagerAdapterBuilder {

        private static final ArrayList<String> TABS = new ArrayList<>();
        private static final ArrayList<BaseMVPDelegate> DELEGATES = new ArrayList<>();
        private static FragmentManager FM = null;

        public IndexDelegateViewPagerAdapter build() {
            return new IndexDelegateViewPagerAdapter(FM, TABS, DELEGATES);
        }

        public IndexDelegateViewPagerAdapterBuilder addFragmentManager(FragmentManager fragmentManager) {
            if (fragmentManager != null) {
                FM = fragmentManager;
            }
            return this;
        }

        public IndexDelegateViewPagerAdapterBuilder addTabList(ArrayList<String> tabs) {
            TABS.clear();
            if (tabs != null && tabs.size() > 0) {
                TABS.addAll(tabs);
            }
            return this;
        }

        public IndexDelegateViewPagerAdapterBuilder addDelegates(ArrayList<BaseMVPDelegate> delegates) {
            DELEGATES.clear();
            if (delegates != null && delegates.size() > 0) {
                DELEGATES.addAll(delegates);
            }
            return this;
        }


    }
}
