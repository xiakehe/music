package com.example.study_core.delegate.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.study_core.delegate.web.route.RouteKeys;
import com.example.study_core.delegate.web.route.Router;

public class WebDelegateImpl extends WebDelegate {
    private IPageLoadListener mIPageLoadListener = null;

    public void setmIPageLoadListener(IPageLoadListener mIPageLoadListener) {
        this.mIPageLoadListener = mIPageLoadListener;
    }

    public static WebDelegateImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public Object setLayout() {

        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if (getUrl() != null) {
            //用原生的方式模拟Web跳转并进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public WebView initWebView(WebView webView) {

        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        if (mIPageLoadListener != null) {
            webViewClient.setmIPageLoadListener(mIPageLoadListener);
        }
        return webViewClient;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getTopDelegate() == this) {
            _mActivity.finish();
        } else {
            pop();
        }
        return true;

    }
}
