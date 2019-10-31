package co.tiangongsky.bxsdkdemo.base;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import co.tiangongsky.bxsdkdemo.util.CustomProgressDialog;
import co.tiangongsky.bxsdkdemo.util.ToastUtil;

public abstract class BasesActivity extends Activity {

    private CustomProgressDialog dialog;

    public void dismissProgressDialog() {
        if (this.dialog != null) {
            if (this.dialog.isShowing())
                this.dialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (this.dialog == null) {
            this.dialog = new CustomProgressDialog(this);
        }
        this.dialog.setTips("加载中...");
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    protected void showToast(final String msg) {
        if (Looper.myLooper() == getMainLooper()) {
            ToastUtil.toast(this, msg);

        } else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.toast(getApplicationContext(), msg);
                }
            });
        }
    }
}
