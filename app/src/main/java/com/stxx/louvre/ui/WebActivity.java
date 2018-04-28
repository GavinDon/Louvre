package com.stxx.louvre.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.stxx.louvre.R;
import com.stxx.louvre.base.BaseActivity;
import com.stxx.louvre.ui.activity.LoginActivity;

import org.jetbrains.annotations.Nullable;

import java.util.Stack;

/**
 * web 页面
 */
public class WebActivity extends BaseActivity {
    private WebView mWebView;//
    private JavaScriptInterface JSInterface;
    private Stack<String> titleStack = new Stack<>();
    private String url;

    @Override
    public int inflateViewId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mWebView = findViewById(R.id.webView);
        url = getIntent().getStringExtra("url");
        initWeb();
        JSInterface = new JavaScriptInterface(this);
        mWebView.addJavascriptInterface(JSInterface, "JSInterface");
    }

    /**
     * 初始化WebView
     */
    private void initWeb() {
        WebSettings webSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            // 设置自动加载图片
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        mWebView.getSettings().setSupportMultipleWindows(false);
        mWebView.getSettings().getAllowFileAccess();
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (!url.isEmpty()){
            mWebView.loadUrl(url);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });


        class ChromeClient extends WebChromeClient {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                urlTitle.setText(title);
                titleStack.push(title);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        }

    }

    /**
     * 和js通迅接口
     *
     * @author linan
     */
    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        /**
         * 跳转登陆页面
         */
        @JavascriptInterface
        public void goLogin() {
            Intent intent = new Intent(this.mContext, LoginActivity.class);
            startActivity(intent);
        }
    }
}
