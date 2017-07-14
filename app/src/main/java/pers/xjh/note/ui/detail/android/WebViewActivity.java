package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.widget.RectProgressBar;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by XJH on 2017/5/2.
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    /** 进度条对象 */
    private RectProgressBar mRectProgressBar;
    /** WebView对象 */
    private WebView mWebView;
    //传入的地址
    private String mWebUrl;

    @Override
    protected int initContentView() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if(intent != null) {
            mWebUrl = intent.getStringExtra(Constant.KEY_WEB_URL);
        }
    }

    protected void initTitle() {
        super.initTitle();
        mTitleBar.setLeftTitleClickListener(this);
    }

    @Override
    protected void initView() {
        initTitle();
        initWebViewSetting();

        mRectProgressBar = (RectProgressBar) findViewById(R.id.progress_bar);

        if(!TextUtils.isEmpty(mWebUrl)) {
            mWebView.loadUrl(mWebUrl);
        } else {
            mWebView.loadUrl("http://www.baidu.com");
        }
    }

    @Override
    public void onBackPressed() {
        if(mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    /**
     * 初始化WebView设置
     */
    private void initWebViewSetting() {
        mWebView = (WebView) findViewById(R.id.web_view);
        //开启chrome测试模式
        mWebView.setWebContentsDebuggingEnabled(true);
        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //页面支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        //打开网页时不调用系统浏览器， 而是在本WebView中显示
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //判断页面加载过程
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100) {
                    mRectProgressBar.setVisibility(View.GONE);
                } else {
                    mRectProgressBar.setVisibility(View.VISIBLE);
                    mRectProgressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_left:
                super.onBackPressed();
                break;
        }
    }
}
