package com.example.study_music.com.xkh.music.luancher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.study_core.app.AccountManager;
import com.example.study_core.app.IUserCheck;
import com.example.study_core.delegate.PermissionDelegate;
import com.example.study_core.util.storage.StudySharedPreference;
import com.example.study_music.R;
import com.example.study_ui.luancher.ILauncherFinish;
import com.example.study_ui.luancher.LauncherHolder;
import com.example.study_ui.luancher.OnLauncherFinishTag;
import com.example.study_ui.luancher.ScrollLauncherTag;

import java.util.ArrayList;

public class LauncherScrollDelegate extends PermissionDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherFinish iLauncherFinish = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherFinish) {
            iLauncherFinish = (ILauncherFinish) activity;
        }
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initConvenientBanner();
    }

    private void initConvenientBanner() {
        INTEGERS.clear();
        INTEGERS.add(R.drawable.launcher_00);
        INTEGERS.add(R.drawable.launcher_01);
        INTEGERS.add(R.drawable.launcher_02);
        INTEGERS.add(R.drawable.launcher_03);
        INTEGERS.add(R.drawable.launcher_04);
        INTEGERS.add(R.drawable.launcher_05);
        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new LauncherHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.launcher_banner;
            }
        }, INTEGERS)
                .setOnItemClickListener(this)
                .setPageIndicator(new int[]{com.example.study_ui.R.drawable.ic_dot_normal, com.example.study_ui.R.drawable.ic_dot_seleted})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setCanLoop(false);
    }

    @Override
    public void onItemClick(int position) {
        if (position == INTEGERS.size() - 1) {
            StudySharedPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            AccountManager.checkSignIn(new IUserCheck() {
                @Override
                public void onSignIn() {
                    if (iLauncherFinish != null) {
                        iLauncherFinish.onLauncherFinish(OnLauncherFinishTag.SIGN);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (iLauncherFinish != null) {
                        iLauncherFinish.onLauncherFinish(OnLauncherFinishTag.NOT_SIGN);
                    }
                }
            });
        }
    }
}
