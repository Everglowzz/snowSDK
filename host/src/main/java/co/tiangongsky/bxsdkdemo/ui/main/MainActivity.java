package co.tiangongsky.bxsdkdemo.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import co.tiangongsky.bxsdkdemo.R;
import co.tiangongsky.bxsdkdemo.base.BasesActivity;


public class MainActivity extends BasesActivity {
    private WebView mWebView;
    private LinearLayout mEjz;
    private LinearLayout mEweb;
    private ImageView mQiu;

    boolean error = false;

    private boolean isfinish = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isfinish) {
                removeBtnBack(mWebView);
                mHandler.sendEmptyMessageDelayed(0, 200);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        initData();
    }

    private void initData() {
        mWebView = findViewById(R.id.web_view);
        mEjz = findViewById(R.id.ejz);
        mEweb = findViewById(R.id.eweb);
        mQiu = findViewById(R.id.qiu);



  /*      mEjz.setVisibility(View.VISIBLE);
//        mEjz.setAlpha(0.5f);
        mEweb.setVisibility(View.GONE);*/


        mQiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isQQClientAvailable(getApplicationContext()) == true) {
                    String us = "mqqwpa://im/chat?chat_type=wpa&uin=2742753770&version=1&src_type=web&web_src=oicqzone.com";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(us));
                    startActivity(intent);
                } else {
                    showToast("客服未接通，请先安装手机QQ");
                }

            }
        });


        mWebView.loadUrl("http://www.chong4.com.cn/mobile/");
        //支持javascript
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
        mHandler.sendEmptyMessageDelayed(0, 200);
        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示

        mWebView.setWebChromeClient(new MyWebCromeClient());



        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //拦截后退按键回到主页
                if (url.equals("http://m.zhaojiafang.com/common/aboutus?zresource=m&zresource=n")){
                    //                    showToast("请拨打客服热线 ！");
                    return true;
                }

                if (url.equals("http://h5.zhaojiafang.com/common/h5?zresource=n&title=%E8%AF%A6%E6%83%85&url=")){
                    //                    showToast("请拨打客服热线 ！");
                    return true;
                }

                if (url.contains("http://wpa.b.qq.com/")){
                    //                    showToast("请拨打客服热线 ！");

                    if (isQQClientAvailable(getApplicationContext())==true){
                        String us = "mqqwpa://im/chat?chat_type=wpa&uin=2742753770&version=1&src_type=web&web_src=oicqzone.com";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(us));
                        startActivity(intent);
                    }else {
                        showToast("客服未接通，请先安装手机QQ");
                    }


                    return true;
                }

                if (url.contains("wpa.qq.com")){
                    //                    showToast("请拨打客服热线 ！");

                    if (isQQClientAvailable(getApplicationContext())==true){
                        String us = "mqqwpa://im/chat?chat_type=wpa&uin=2742753770&version=1&src_type=web&web_src=oicqzone.com";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(us));
                        startActivity(intent);
                    }else {
                        showToast("客服未接通，请先安装手机QQ");
                    }


                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);

            }



            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissProgressDialog();

                //                view.loadUrl("JavaScript:function mFunc(){document.querySelector('#logo').querySelector('img').src=\"http://photocdn.sohu.com/20150906/mp30746702_1441530698601_2_th.jpg\";}mFunc();");            }


                //编写 javaScript方法
           /*     String javascript = "javascript:function hideOther() {" +
                        "document.getElementsByTagName('body')[0].innerHTML;" +
                        "document.getElementsByClassName('logo')[0].style.display='none';" +
                        "document.getElementsByTagName('remind-main')[0].remove();" +
                    "document.getElementsByClassName('tips')[1].remove();}";


                //创建方法
                view.loadUrl(javascript);

                //加载方法
                view.loadUrl("javascript:hideOther();");*/


            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //                showErrorPage();//显示错误页面
                mWebView.loadUrl("file:///android_asset/Error.html");
                error = true;
            }

        });
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }



    private class MyWebCromeClient extends WebChromeClient {

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("").setMessage(message);

            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);
            builder.setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(et.getText().toString());
                        }

                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
                    return true;
                }
            });

            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }


        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("")
                    .setMessage(message)
                    .setPositiveButton("确定", null);

            // 不需要绑定按键事件
            // 屏蔽keycode等于84之类的按键
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
                    return true;
                }
            });
            // 禁止响应按back键的事件
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
            return true;
            // return super.onJsAlert(view, url, message, result);

        }


        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("")
                    .setMessage(message)
                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            result.confirm();
                        }
                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    result.cancel();
                }
            });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
                    return true;
                }
            });
            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
            // return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            removeBtnBack(view);
            if (newProgress == 100) {
                //加载完毕进度条消失
                //                mEjz.setVisibility(View.GONE);
                //                mEweb.setVisibility(View.VISIBLE);
                dismissProgressDialog();
            } else {
                //更新进度
                //                mEjz.setVisibility(View.VISIBLE);
                //                mEweb.setVisibility(View.GONE);
                showProgressDialog();
            }


            super.onProgressChanged(view, newProgress);
        }


    }

    private void remove(WebView webView) {
        //删除唯彩看球 首页标题
        String javascript = "javascript:function hideOther() {document.getElementsByClassName('public_header public_header_xzys')[0].style.display='none';}";
        webView.loadUrl(javascript);
        webView.loadUrl("javascript:hideOther();");


        javascript = "javascript:function hideOther() {document.getElementsByClassName('fl index-footer-list')[1].style.display='none';}";
        webView.loadUrl(javascript);
        webView.loadUrl("javascript:hideOther();");

    }

    private void removeBtnBack(WebView view) {
        String javascript;

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('home-camera')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('fc-camera')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('home-phone')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('si-div2')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('icon-phone')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('icon-qq')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('sofooter')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('sospan2')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('so-span1')[0].remove();}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('sispan7')[0].remove();}";
        view.loadUrl(javascript);



        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('home-icon')[0].getElementsByTagName('li')[4].style.display='none';}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('go-operation')[0].getElementsByTagName('li')[0].style.display='none';}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('go-operation')[0].getElementsByTagName('li')[3].style.display='none';}";
        view.loadUrl(javascript);

        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('go-operation')[0].getElementsByTagName('li')[4].style.display='none';}";
        view.loadUrl(javascript);



        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "document.getElementsByClassName('uc-userul')[0].getElementsByTagName('img')[8].style.display='none';}";
        view.loadUrl(javascript);





        view.loadUrl("javascript:hideOther();");
        javascript = "javascript:function hideOther() {" +
                "var divs = document.getElementById('apptip');" +
                "divs.remove(); }";
        view.loadUrl(javascript);

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isfinish = true;
    }
}

