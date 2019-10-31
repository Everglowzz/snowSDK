package co.tiangongsky.bxsdkdemo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import co.tiangongsky.bxsdkdemo.R;


public class FollowNewActivity extends Activity {

    private WebView mWebView;
    private String mUrl;
    boolean error = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follownew);
        mWebView = findViewById(R.id.web_view);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();

        mUrl = intent.getStringExtra("url");




        mWebView.loadUrl(mUrl);

        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(false);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setDomStorageEnabled(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.getSettings().setDomStorageEnabled(true);// 支持使用localStorage(H5页面的支持)
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());

        mWebView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //                showErrorPage();//显示错误页面
                mWebView.loadUrl("file:///android_asset/Error.html");
                error = true;
            }

        });

    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

}
