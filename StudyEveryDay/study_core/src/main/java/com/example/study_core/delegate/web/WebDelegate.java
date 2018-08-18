package com.example.study_core.delegate.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.study_core.delegate.PermissionDelegate;
import com.example.study_core.delegate.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public abstract class WebDelegate extends PermissionDelegate implements IWebViewInitializer {
    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private WebDelegate mTopDelegate;
    private String mUrl = null;
    protected String mTitle = null;
    private boolean mIsWebViewAvailable = false;

    protected WebDelegate() {
    }

    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
        mTitle = args.getString(RouteKeys.TITLE.name());
        initWebView();
    }

    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            final IWebViewInitializer initializer = setInitializer();
            if (initializer == null) {
                throw new NullPointerException("initializer is null");
            }
            final WeakReference<WebView> webViewWeakReference = new WeakReference<>(new WebView(getContext()), WEB_VIEW_QUEUE);
            mWebView = initializer.initWebView(webViewWeakReference.get());
            mWebView.setWebChromeClient(initializer.initWebChromeClient());
            mWebView.setWebViewClient(initializer.initWebViewClient());
            mIsWebViewAvailable = true;
        }
    }

    public WebView getWebView() {
        if (mWebView == null) {
            throw new NullPointerException("webView IS NULL ");
        }
        return mIsWebViewAvailable ? mWebView : null;
    }

    protected String getUrl() {
        if (mUrl == null) {
            throw new NullPointerException("WebView IS NULL!");
        }
        return mUrl;
    }


    public void setmTopDelegate(WebDelegate mTopDelegate) {
        this.mTopDelegate = mTopDelegate;
    }

    public WebDelegate getTopDelegate() {
        if (mTopDelegate == null) {
            mTopDelegate = this;
        }
        return mTopDelegate;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }
}
