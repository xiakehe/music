package com.example.study_music.com.xkh.music.luancher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.example.study_core.app.AccountManager;
import com.example.study_core.app.IUserCheck;
import com.example.study_core.delegate.PermissionDelegate;
import com.example.study_core.timer.BaseTimerTask;
import com.example.study_core.timer.ITimerListener;
import com.example.study_core.util.storage.StudySharedPreference;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_ui.luancher.ILauncherFinish;
import com.example.study_ui.luancher.OnLauncherFinishTag;
import com.example.study_ui.luancher.ScrollLauncherTag;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

public class LauncherDelegate extends PermissionDelegate implements ITimerListener {
    private Timer mTimer = null;
    private int mCount = 5;

    private ILauncherFinish iLauncherFinish = null;


    @BindView(R2.id.launcher_jump_text)
    TextView mLauncherJumpText = null;

    @OnClick(R2.id.launcher_jump_text)
    void onClickLauncherJumpText() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIntoScrollDelegate();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherFinish) {
            iLauncherFinish = (ILauncherFinish) activity;
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initTimer();
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask timerTask = new BaseTimerTask(this);
        mTimer.schedule(timerTask, 0, 1000);
    }

    @Override
    public void onTimer() {

        FragmentActivity activity = getActivity();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mLauncherJumpText != null) {
                        mLauncherJumpText.setText(MessageFormat.format("跳过\n {0}s", mCount));
                        mCount--;
                    }
                    if (mTimer != null) {
                        if (mCount < 0) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIntoScrollDelegate();
                        }
                    }
                }
            });
        }


    }

    private void checkIntoScrollDelegate() {
        if (!StudySharedPreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            getSupportDelegate().startWithPop(new LauncherScrollDelegate());
        } else {
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
