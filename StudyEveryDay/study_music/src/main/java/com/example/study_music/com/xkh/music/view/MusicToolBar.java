package com.example.study_music.com.xkh.music.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.study_music.R;


public class MusicToolBar extends Toolbar implements View.OnClickListener, View.OnFocusChangeListener {

    private TextView mLeftTitle;
    private TextView mCenterTitle;
    private EditText mSearch;
    private TextView mTimeIcon;
    private TextView mDownloadIcon;
    private TextView mSearchIcon;
    private TextView mEmailIcon;
    private TextView mSettingIcon;

    private ImageView mReturn;

    private String mCenterText, mLeftText;
    private ToolBarOnClickListener mToolBarOnClickListener = null;
    private ToolBarOnClickListener.onFocusChangeListener mFocusChangeListener = null;
    private ToolBarNormalSearchClickListener mNormalSearchClickListener = null;


    public MusicToolBar(Context context) {
        super(context);
        inflate(context, R.layout.delegate_toolbar, this);
    }

    public MusicToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.delegate_toolbar, this);

    }

    public MusicToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.delegate_toolbar, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {

        mLeftTitle = findViewById(R.id.left_title);
        mCenterTitle = findViewById(R.id.main_title);
        mSearch = findViewById(R.id.toolbar_edit_search);
        mTimeIcon = findViewById(R.id.toolbar_icon_time);
        mDownloadIcon = findViewById(R.id.toolbar_icon_download);
        mSearchIcon = findViewById(R.id.toolbar_icon_search);

        mReturn = findViewById(R.id.iv_return);
        mReturn.setOnClickListener(this);

        mEmailIcon = findViewById(R.id.toolbar_icon_email);
        mEmailIcon.setOnClickListener(this);
        mSettingIcon = findViewById(R.id.toolbar_icon_setting);
        initListener();
    }

    private void initListener() {
        mLeftTitle.setOnClickListener(this);
        mEmailIcon.setOnClickListener(this);
        mSettingIcon.setOnClickListener(this);
        mTimeIcon.setOnClickListener(this);
        mDownloadIcon.setOnClickListener(this);
        mSearchIcon.setOnClickListener(this);
        mSearch.setOnFocusChangeListener(this);
    }


    public void setCenterText(String mCenterText) {
        this.mCenterText = mCenterText;
    }

    public void setmLeftTitle(String mLeftTitle) {
        this.mLeftText = mLeftTitle;
    }

    public void setToolBarOnClickListener(ToolBarOnClickListener mToolBarOnClickListener) {
        this.mToolBarOnClickListener = mToolBarOnClickListener;
    }

    public ToolBarNormalSearchClickListener getmNormalSearchClickListener() {
        return mNormalSearchClickListener;
    }

    public void setmNormalSearchClickListener(ToolBarNormalSearchClickListener mNormalSearchClickListener) {
        this.mNormalSearchClickListener = mNormalSearchClickListener;
    }

    public void setOnFocusChangeListener(ToolBarOnClickListener.onFocusChangeListener onFocusChangeListener) {
        this.mFocusChangeListener = onFocusChangeListener;
    }


    public void reInitViewByMode(ToolBarType toolBarType, boolean isLogin) {

        switch (toolBarType) {

            case NORMAL:
                if (mReturn.getVisibility() != VISIBLE) {
                    mReturn.setVisibility(VISIBLE);
                }
                if (mCenterTitle.getVisibility() != VISIBLE) {
                    mCenterTitle.setVisibility(VISIBLE);
                }
                mCenterTitle.setText(mCenterText);
                if (mLeftTitle.getVisibility() != GONE) {
                    mLeftTitle.setVisibility(GONE);
                }
                if (mEmailIcon.getVisibility() != GONE) {
                    mEmailIcon.setVisibility(GONE);
                }

                if (mTimeIcon.getVisibility() != GONE) {
                    mTimeIcon.setVisibility(GONE);
                }
                if (mSearch.getVisibility() != GONE) {
                    mSearch.setVisibility(GONE);
                }
                if (mSearchIcon.getVisibility() != VISIBLE) {
                    mSearchIcon.setVisibility(VISIBLE);
                }
                if (mSettingIcon.getVisibility() != GONE) {
                    mSettingIcon.setVisibility(GONE);
                }
                if (mDownloadIcon.getVisibility() != GONE) {
                    mDownloadIcon.setVisibility(GONE);
                }

                break;
            case HOME:

                if (isLogin) {
                    if (mEmailIcon.getVisibility() != VISIBLE) {
                        mEmailIcon.setVisibility(VISIBLE);
                    }
                    if (mLeftTitle.getVisibility() != GONE) {
                        mLeftTitle.setVisibility(GONE);
                    }
                    if (mSettingIcon.getVisibility() != GONE) {
                        mSettingIcon.setVisibility(GONE);
                    }

                } else {
                    if (mLeftTitle.getVisibility() != VISIBLE) {
                        mLeftTitle.setText(R.string.login);
                        mLeftTitle.setVisibility(VISIBLE);
                    }
                    if (mSettingIcon.getVisibility() != GONE) {
                        mSettingIcon.setVisibility(GONE);
                    }
                    if (mEmailIcon.getVisibility() != GONE) {
                        mEmailIcon.setVisibility(GONE);
                    }
                }
                if (mReturn.getVisibility() != GONE) {
                    mReturn.setVisibility(GONE);
                }
                if (mSearchIcon.getVisibility() != GONE) {
                    mSearchIcon.setVisibility(GONE);
                }
                if (mCenterTitle.getVisibility() != GONE) {
                    mCenterTitle.setVisibility(GONE);
                }
                if (mSearch.getVisibility() != VISIBLE) {
                    mSearch.setVisibility(VISIBLE);
                }
                if (mTimeIcon.getVisibility() != VISIBLE) {
                    mTimeIcon.setVisibility(VISIBLE);
                }
                if (mDownloadIcon.getVisibility() != VISIBLE) {
                    mDownloadIcon.setVisibility(VISIBLE);
                }
                break;

            case LISTENER:
            case VIDEO:
                if (isLogin) {
                    if (mEmailIcon.getVisibility() != VISIBLE) {
                        mEmailIcon.setVisibility(VISIBLE);
                    }
                    if (mLeftTitle.getVisibility() != GONE) {
                        mLeftTitle.setVisibility(GONE);
                    }
                } else {
                    mLeftTitle.setVisibility(GONE);
                    mEmailIcon.setVisibility(GONE);
                    mSettingIcon.setVisibility(GONE);
                }
                mReturn.setVisibility(GONE);
                mSearch.setVisibility(GONE);
                mTimeIcon.setVisibility(GONE);
                mDownloadIcon.setVisibility(GONE);
                mSearchIcon.setVisibility(VISIBLE);
                mCenterTitle.setVisibility(VISIBLE);
                mCenterTitle.setText(mCenterText);
                break;

            case ME:
                mReturn.setVisibility(GONE);
                mEmailIcon.setVisibility(VISIBLE);
                mSettingIcon.setVisibility(VISIBLE);
                mCenterTitle.setVisibility(GONE);
                mSearchIcon.setVisibility(GONE);
                mDownloadIcon.setVisibility(GONE);
                mTimeIcon.setVisibility(GONE);
                mLeftTitle.setVisibility(GONE);
                mSearch.setVisibility(GONE);
                break;
            case LEFT:

                mReturn.setVisibility(VISIBLE);
                mCenterTitle.setVisibility(GONE);
                mSearchIcon.setVisibility(GONE);
                mDownloadIcon.setVisibility(GONE);
                mTimeIcon.setVisibility(GONE);
                mLeftTitle.setText(mLeftText);
                mLeftTitle.setVisibility(VISIBLE);
                mSearch.setVisibility(GONE);
                mEmailIcon.setVisibility(GONE);
                break;


            default:
                break;
        }


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (mFocusChangeListener != null) {
            mFocusChangeListener.onFocusChangeListen(hasFocus);
        }

    }

    public interface ToolBarOnClickListener {


        void onClickLogin();

        void onClickDownLoad();

        void onClickTime();

        void onClickEmail();

        void onClickSetting();

        void onClickSearch();

        interface onFocusChangeListener {
            void onFocusChangeListen(boolean hasFocus);
        }
    }

    public interface ToolBarNormalSearchClickListener {

        void onReturn();

        void onClickSearch();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (mToolBarOnClickListener != null) {

            if (i == R.id.left_title) {
                mToolBarOnClickListener.onClickLogin();
            } else if (i == R.id.toolbar_icon_email) {
                mToolBarOnClickListener.onClickEmail();
            } else if (i == R.id.toolbar_icon_setting) {
                mToolBarOnClickListener.onClickSetting();
            } else if (i == R.id.toolbar_icon_time) {
                mToolBarOnClickListener.onClickTime();
            } else if (i == R.id.toolbar_icon_download) {
                mToolBarOnClickListener.onClickDownLoad();
            } else if (i == R.id.toolbar_icon_search) {
                mToolBarOnClickListener.onClickSearch();
            }
        } else if (mNormalSearchClickListener != null) {
            if (i == R.id.iv_return) {
                mNormalSearchClickListener.onReturn();
            } else if (i == R.id.toolbar_icon_search) {
                mNormalSearchClickListener.onClickSearch();
            }
        }


    }
}
