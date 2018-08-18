package com.example.study_music.com.xkh.music.main.personal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.study_core.app.AccountManager;
import com.example.study_core.app.Study;
import com.example.study_core.delegate.buttom.BottomItemDelegate;
import com.example.study_core.rx.Event;
import com.example.study_core.rx.RxJava2Manager;
import com.example.study_core.util.ToastHelp;
import com.example.study_core.util.file.ACache;
import com.example.study_core.util.log.StudyLogger;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.bean.User;
import com.example.study_music.com.xkh.music.sign.SignInActivity;
import com.example.study_music.com.xkh.music.sign.SignInDelegate;
import com.example.study_music.com.xkh.music.view.MusicToolBar;
import com.example.study_music.com.xkh.music.view.ToolBarType;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class PersonalDelegate extends BottomItemDelegate implements View.OnClickListener {

    @BindView(R2.id.rv_mine)
    RecyclerView mList;
    @BindView(R2.id.tool_bar_layout)
    MusicToolBar toolBar = null;

    private final ArrayList<PersonalSection> personalSections = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        initToolBar();
        initRecList();
    }

    protected void notifySignInOut(User user) {
        if (user != null) {
            StudyLogger.e("xiakehe", Study.isUserLogin() + "is login" + user.toString());
        }
    }


    private void initToolBar() {
        toolBar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_normal_color));
        toolBar.reInitViewByMode(ToolBarType.ME, Study.isUserLogin());
        RxJava2Manager.getRxJava2ManagerInstance().ConsumeEvent(new Consumer<Object>() {
            @SuppressWarnings("unchecked")
            @Override
            public void accept(Object o) {
                if (o instanceof Event && ((Event) o).getEventInfo() == Event.EVENT_INFO.SIGN_IN) {

                    notifySignInOut((User) ((Event) o).getObj());
                } else if (((Event) o).getEventInfo() == Event.EVENT_INFO.SIGN_OUT) {
                    notifySignInOut(null);
                }
            }
        });

    }

    private static final String[] titles = new String[]{
            "我的消息", "商城", "我的积分",
            "优惠券", "", "个性换肤", "听歌识曲", "",
            "定时停止播放",
            "扫一扫", "音乐闹钟", "驾驶模式", "音乐云盘", "系统设置", "退出"

    };
    private static final int[] resId = new int[]{
            R.drawable.ic_email_gray, R.drawable.ic_buy_cart, R.drawable.ic_bonus, R.drawable.ic_youhui,
            0, R.drawable.ic_change_theme, R.drawable.ic_listen_music, 0, R.drawable.ic_time_stop, R.drawable.ic_scan, R.drawable.ic_alarm, R.drawable.ic_drive_mode,
            R.drawable.ic_cloud, R.drawable.ic_setting_mine, R.drawable.ic_exit
    };

    private void initRecList() {

        personalSections.clear();
        int i = titles.length;
        for (int j = 0; j < i; j++) {
            if (TextUtils.isEmpty(titles[j])) {
                PersonalSection section = new PersonalSection(true, null);
                personalSections.add(section);
            } else {
                PersonItem item = new PersonItem(resId[j], titles[j], true);
                PersonalSection section = new PersonalSection(item);
                personalSections.add(section);
            }

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        PersonalSectionAdapter adapter = new PersonalSectionAdapter(R.layout.adapter_mine_list_item, R.layout.adapter_mine_list_line_item, personalSections);

        @SuppressLint("InflateParams")
        View mHeadView = LayoutInflater.from(mContext).inflate(R.layout.delegate_person_header, null);
        RelativeLayout rl_login = mHeadView.findViewById(R.id.rl_login);
        rl_login.setOnClickListener(this);
        TextView localMusic = mHeadView.findViewById(R.id.tv_local_music);
        localMusic.setOnClickListener(this);
        adapter.addHeaderView(mHeadView);
        mList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String title = personalSections.get(position).t.getTitle();
                if (TextUtils.equals(title, "退出") && Study.isUserLogin()) {
                    AccountManager.setSingState(false);
                    RxJava2Manager.getRxJava2ManagerInstance().sendEvent(new Event<>(null, Event.EVENT_INFO.SIGN_OUT));
                    ToastHelp.showLong(mContext, "退出登录！");
                } else {
                    ACache.get(Study.getApplicationContext()).clear();

                    ToastHelp.showLong(mContext, "请先登录！");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_login) {
            getParentDelegate().start(new SignInDelegate());
        } else if (i == R.id.tv_local_music) {

            getParentDelegate().start(new LocalMusicDelegate());
        }
    }
}
