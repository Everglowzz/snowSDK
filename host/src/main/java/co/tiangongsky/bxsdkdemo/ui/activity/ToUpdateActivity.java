package co.tiangongsky.bxsdkdemo.ui.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import co.tiangongsky.bxsdkdemo.R;
import co.tiangongsky.bxsdkdemo.update.Constants;
import co.tiangongsky.bxsdkdemo.update.InstallUtils;
import co.tiangongsky.bxsdkdemo.update.PermissionUtils;


public class ToUpdateActivity extends Activity {
    public InstallUtils.DownloadCallBack downloadCallBack;
    private Activity context;
    //  进度条
    private ProgressBar mProgressBar;

    //  请求链接
    private String mUrl;


    private TextView tv_progress;
    private String apkDownloadPath;
    private TextView tv_info;
    private final int REQUEST_CODE_SD_CARD = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_toupdate);
        context = this;
        mProgressBar = (ProgressBar) findViewById(R.id.pb_Progress);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_info = (TextView) findViewById(R.id.tv_info);
        Intent intent = getIntent();

        mUrl = intent.getStringExtra("url");

        initCallBack();

        if (!PermissionUtils.isGrantSDCardReadPermission(this)) {
            // 没权限申请权限
            PermissionUtils.requestSDCardReadPermission(this, REQUEST_CODE_SD_CARD);
        } else {
            // 有权限就下载
            downloadApk();
        }
    }

    private void downloadApk() {
        InstallUtils.with(this)
                //必须-下载地址
                .setApkUrl(mUrl)
                //非必须-下载保存的文件的完整路径+name.apk
                .setApkPath(Constants.APK_SAVE_PATH)
                //非必须-下载回调
                .setCallBack(downloadCallBack)
                //开始下载
                .startDownload();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_SD_CARD:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 成功
                    downloadApk();
                } else {

                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initCallBack() {
        downloadCallBack = new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                tv_progress.setText("0%");
                mProgressBar.setProgress(0);
            }

            @Override
            public void onComplete(String path) {

                apkDownloadPath = path;
                tv_progress.setText("100%");
                tv_progress.setVisibility(View.GONE);
                mProgressBar.setProgress(100);
                mProgressBar.setVisibility(View.GONE);
                tv_info.setVisibility(View.VISIBLE);
                //先判断有没有安装权限
                InstallUtils.checkInstallPermission(context, new InstallUtils.InstallPermissionCallBack() {
                    @Override
                    public void onGranted() {
                        //去安装APK
                        installApk(apkDownloadPath);
                    }

                    @Override
                    public void onDenied() {
                        //弹出弹框提醒用户
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("温馨提示")
                                .setMessage("必须授权才能安装APK，请设置允许安装")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //打开设置页面
                                        InstallUtils.openInstallPermissionSetting(context, new InstallUtils.InstallPermissionCallBack() {
                                            @Override
                                            public void onGranted() {
                                                //去安装APK
                                                installApk(apkDownloadPath);
                                            }

                                            @Override
                                            public void onDenied() {
                                                //还是不允许咋搞？
                                                Toast.makeText(context, "不允许安装咋搞？强制更新就退出应用程序吧！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onLoading(long total, long current) {
                //内部做了处理，onLoading 进度转回progress必须是+1，防止频率过快
                int progress = (int) (current * 100 / total);
                tv_progress.setText(progress + "%");
                mProgressBar.setProgress(progress);
            }

            @Override
            public void onFail(Exception e) {
            }

            @Override
            public void cancle() {
            }
        };
    }

    private void installApk(String path) {
        InstallUtils.installAPK(context, path, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                //onSuccess：表示系统的安装界面被打开
                //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
                Toast.makeText(getApplicationContext(), "正在安装程序", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Exception e) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置监听,防止其他页面设置回调后当前页面回调失效
        if (InstallUtils.isDownloading()) {
            InstallUtils.setDownloadCallBack(downloadCallBack);
        }
    }
}
