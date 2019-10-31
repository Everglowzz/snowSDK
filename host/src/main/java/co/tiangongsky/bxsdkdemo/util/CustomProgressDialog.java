package co.tiangongsky.bxsdkdemo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import co.tiangongsky.bxsdkdemo.R;


public class CustomProgressDialog extends Dialog {
    TextView tips;

    public CustomProgressDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        getContext().setTheme(android.R.style.Theme_InputMethod);
        super.setContentView(R.layout.progress_dialog_layout);
        this.tips = findViewById(R.id.tips);
        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = 2;
        attributesParams.dimAmount = 0.8f;
        attributesParams.width = -2;
        attributesParams.height = -2;
        window.setAttributes(attributesParams);
    }

    public void setTips(String tips) {
        this.tips.setText(tips);
    }

    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}
