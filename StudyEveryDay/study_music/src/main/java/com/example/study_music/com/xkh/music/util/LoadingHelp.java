package com.example.study_music.com.xkh.music.util;

import android.view.View;
import android.widget.LinearLayout;

public class LoadingHelp {

    private LinearLayout loading;
    private LinearLayout load_error;
    private ClickLoadingError loadingErrorListener;

    private void init() {
        if (load_error == null) {
            return;
        }
        resetGone(load_error);
        load_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (load_error.getVisibility() == View.VISIBLE && loadingErrorListener != null) {
                    loadingErrorListener.refresh();
                }
            }
        });
    }

    public void setLoad_error(LinearLayout load_error, ClickLoadingError loadingErrorListener) {
        this.load_error = load_error;
        this.loadingErrorListener = loadingErrorListener;
        init();
    }

    public LoadingHelp(LinearLayout loading) {
        this.loading = loading;
        init();
    }

    public LoadingHelp(LinearLayout load_error, ClickLoadingError loadingErrorListener) {
        this.load_error = load_error;
        this.loadingErrorListener = loadingErrorListener;
        init();
    }

    private boolean isNotNull(View view) {
        return view != null;
    }

    public void startLoading() {
        if (!isNotNull(loading)) {
            return;
        }
        showView(loading);
        resetGone(load_error);
    }

    public void stopLoading() {

        resetGone(loading);
        resetGone(load_error);
    }

    public void loadError() {

        resetGone(loading);
        showView(load_error);
    }

    private void showView(View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void resetGone(View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    public interface ClickLoadingError {
        void refresh();
    }

    public void onFinish() {

        if (!isNotNull(loading)) {
            loading = null;

        }
        if (!isNotNull(load_error)) {
            load_error = null;

        }
        if (this.loadingErrorListener != null) {
            this.loadingErrorListener = null;
        }
    }
}
