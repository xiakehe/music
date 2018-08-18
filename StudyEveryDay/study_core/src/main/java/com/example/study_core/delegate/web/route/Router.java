package com.example.study_core.delegate.web.route;


import android.webkit.URLUtil;
import android.webkit.WebView;

import com.example.study_core.delegate.web.WebDelegate;
import com.example.study_core.delegate.web.WebDelegateImpl;

public class Router {

    private Router() {

    }

    private static class RouterHolder {
        private static final Router instance = new Router();
    }

    public static Router getInstance() {
        return RouterHolder.instance;
    }

    public final boolean handleWebUrl(WebDelegate webDelegate, String url) {

        if (webDelegate == null) {
            return false;
        }

        final WebDelegate topDelegate = webDelegate.getTopDelegate();

        final WebDelegateImpl delegate = WebDelegateImpl.create(url);
        topDelegate.getSupportDelegate().start(delegate);


        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView == null) {
            throw new NullPointerException("webview is null");
        }
        webView.loadUrl(url);
    }

    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public final void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }
}
