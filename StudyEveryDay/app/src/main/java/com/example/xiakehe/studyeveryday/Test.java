package com.example.xiakehe.studyeveryday;


import android.content.Intent;

import com.example.newmvp.BaseMVPDelegate;
import com.example.study_music.com.xkh.music.luancher.LauncherDelegate;
import com.example.study_music.com.xkh.music.main.RootDelegateBaseActivity;
import com.example.study_ui.luancher.ILauncherFinish;
import com.example.study_ui.luancher.OnLauncherFinishTag;
import com.example.xiakehe.studyeveryday.app.MainActivity;


public class Test extends RootDelegateBaseActivity implements ILauncherFinish {

    @Override
    public void onLauncherFinish(OnLauncherFinishTag finishTag) {

        switch (finishTag) {
            case SIGN:
                startActivity(new Intent(Test.this, MainActivity.class));
                overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out);
                finish();
                break;
            case NOT_SIGN:
                startActivity(new Intent(Test.this, MainActivity.class));
                overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out);
                finish();
                break;
            default:
                break;

        }
    }

    @Override
    public BaseMVPDelegate setRootDelegate() {
        return new LauncherDelegate();
    }
}
