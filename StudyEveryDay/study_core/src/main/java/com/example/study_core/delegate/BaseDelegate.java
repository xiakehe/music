package com.example.study_core.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.study_core.util.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseDelegate extends SupportFragment  {


    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;
    protected Context mContext;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getContext();
        ScreenUtils.setCustumDensity(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected void showView(View view, boolean show) {
        if (view != null) {
            view.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    protected void postRunnable(Runnable runnable) {
        new Handler().postDelayed(runnable, 200);
    }


    @SuppressWarnings("unchecked")
    public <T extends BaseDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
