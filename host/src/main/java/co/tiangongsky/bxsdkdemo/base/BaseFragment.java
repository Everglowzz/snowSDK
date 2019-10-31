package co.tiangongsky.bxsdkdemo.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import co.tiangongsky.bxsdkdemo.util.CustomProgressDialog;


public abstract class BaseFragment extends Fragment {
    private CustomProgressDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), null);

        initData();
        return view;
    }

    protected abstract int getContentLayout();



    protected void initData() {

    }

    public void dismissProgressDialog() {
        if (this.dialog != null) {
            if (this.dialog.isShowing())
                this.dialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (this.dialog == null) {
            this.dialog = new CustomProgressDialog(getContext());
        }
        this.dialog.setTips("加载中...");
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    protected void showToast(String content) {
        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
    }



    protected void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void mStartActivity(Class className){
        startActivity(new Intent(getContext(),className));
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
