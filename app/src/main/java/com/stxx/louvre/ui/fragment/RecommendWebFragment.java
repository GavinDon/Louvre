package com.stxx.louvre.ui.fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.stxx.louvre.R;
import com.stxx.louvre.base.BaseFragment;
import com.stxx.louvre.base.Constant;
import com.stxx.louvre.entity.event.BackEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Stack;

/**
 * description:分类列表 挂WAP
 * Created by liNan on 2018/4/26 18:20
 */
public class RecommendWebFragment extends BaseFragment {

    private WebView mWebView;//
    private String url = Constant.CLASSIFY_URL;
    //保存title集合
    private Stack<String> titleStack = new Stack<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_recommend_web, null, false);
        mWebView = mView.findViewById(R.id.recommendWebView);
        return mView;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        initWeb();
    }

    @Subscribe
    public void onEvent(BackEvent event) {
        onKeyDown(event.getCode(), event.getEvent());
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
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
        mWebView.loadUrl(url);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == 4) && mWebView.canGoBack()) {
            mWebView.goBack(); // goBack()表示返回WebView的上一页面
            //pop 的是当前页面的标题 所以Pop之后取栈中最后一个值
            if (titleStack.size() > 1) {
                titleStack.pop();
            }
            return true;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
