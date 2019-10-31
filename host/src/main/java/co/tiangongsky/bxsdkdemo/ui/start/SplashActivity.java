package co.tiangongsky.bxsdkdemo.ui.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import co.tiangongsky.bxsdkdemo.R;
import co.tiangongsky.bxsdkdemo.bean.skip;
import co.tiangongsky.bxsdkdemo.bean.qiangge;
import co.tiangongsky.bxsdkdemo.network.GsonRequest;
import co.tiangongsky.bxsdkdemo.network.NetworkListener;
import co.tiangongsky.bxsdkdemo.network.NetworkManger;
import co.tiangongsky.bxsdkdemo.ui.activity.FollowNewActivity;
import co.tiangongsky.bxsdkdemo.ui.activity.ToUpdateActivity;
import co.tiangongsky.bxsdkdemo.ui.main.MainActivity;


public class SplashActivity extends Activity  implements ViewPropertyAnimatorListener  {

    private ImageView mImageView;
    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    public void init(){
        mImageView = findViewById(R.id.imageView);
        //缩放动画  3秒钟 跳转进入到主界面中
        skip();

    }

    public void skip(){
        BmobQuery<skip> bmobQuery = new BmobQuery<skip>();
        bmobQuery.getObject("DN2oFFFS", new QueryListener<skip>() {
            @Override
            public void done(skip object, BmobException e) {
                if(e==null){
                    if(object.control>0){
                        String url = object.appUrl;
                        if (url.contains(".apk")){
                            Intent intent = new Intent(getApplicationContext(), ToUpdateActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                        }else {
                                Intent intent = new Intent(getApplicationContext(), FollowNewActivity.class);
                                intent.putExtra("url", url);
                                startActivity(intent);

                        }
                    }else{
                        startAnimate();
                    }
                }else{
                    startAnimate();
                }
            }
        });
    }
    
    public void startAnimate(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(mImageView).scaleX(1.0f).scaleY(1.0f).setListener(SplashActivity.this).setDuration(1500);
            }
        });
    }

    @Override
    public void onAnimationEnd(View view) {
        String dz = "http://appid.985-985.com:8088/getAppConfig.php?appid=snow1";
        GsonRequest<qiangge> request = new GsonRequest<qiangge>(qiangge.class, dz, new NetworkListener<qiangge>() {

            @Override
            public void onResponse(qiangge response) {
                String url = response.getUrl();
                String showWeb = response.getShowWeb();
                String webs = "1";
                String pushKey = response.getPushKey();
                boolean success = response.isSuccess();
                Log.e(TAG,"url:"+url);
                Log.e(TAG,"showWeb:"+showWeb);
                Log.e(TAG,"success:"+success);



                if (success==true){

                    if (url.equals("http://") || pushKey.equals("http://")){
                        Intent intent = new Intent(getApplication(), StartActivity.class);
                        startActivity(intent);
                    }else {

                        if (showWeb.equals(webs)){

                            String pkg ="com.bxvip.app.bx985zy";


                            if (isAvilible(getApplicationContext(),pkg)){

                                PackageManager packageManager = getApplicationContext().getPackageManager();
                                Intent intent1=new Intent();
                                intent1 =packageManager.getLaunchIntentForPackage("com.bxvip.app.bx985zy");
                                if(intent1==null){
                                    Toast.makeText(getApplicationContext(), "未安装", Toast.LENGTH_LONG).show();
                                }else{
                                    startActivity(intent1);
                            }


                            }else {

                                if (url.contains(".apk")||pushKey.contains(".apk")){
                                    Intent intent = new Intent(getApplicationContext(), ToUpdateActivity.class);
                                    intent.putExtra("url", url);
                                    startActivity(intent);
                                }else {

                                    if (!pushKey.equals("")||!url.equals("")){
                                        Intent intent = new Intent(getApplicationContext(), FollowNewActivity.class);
                                        intent.putExtra("url", url);
                                        startActivity(intent);
                                    }

                                }
                            }

                        }else {
                            Intent intent = new Intent(getApplication(), StartActivity.class);
                            startActivity(intent);
                        }

                    }


                }else {
                    String pkg ="com.bxvip.app.bx985zy";
                    if (isAvilible(getApplicationContext(),pkg)){

                        PackageManager packageManager = getPackageManager();
                        Intent intent1=new Intent();
                        intent1 =packageManager.getLaunchIntentForPackage("com.bxvip.app.bx985zy");
                        if(intent1==null){
                            Toast.makeText(getApplicationContext(), "未安装", Toast.LENGTH_LONG).show();
                        }else{
                            startActivity(intent1);
                        }


                    }else {
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }
                }



            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                String pkg ="com.bxvip.app.bx985zy";
                if (isAvilible(getApplicationContext(),pkg)){

                    PackageManager packageManager = getPackageManager();
                    Intent intent1=new Intent();
                    intent1 =packageManager.getLaunchIntentForPackage("com.bxvip.app.bx985zy");
                    if(intent1==null){
                        Toast.makeText(getApplicationContext(), "未安装", Toast.LENGTH_LONG).show();
                    }else{
                        startActivity(intent1);
                    }


                }else {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                }
            }

        });

        NetworkManger.sendRequest(request);

    }

    @Override
    public void onAnimationStart(View view) {

    }

    @Override
    public void onAnimationCancel(View view) {

    }


    private boolean isAvilible(Context context, String packageName ){

        final PackageManager packageManager = context.getPackageManager();

        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for ( int i = 0; i < pinfo.size(); i++ )
        {
            // 循环判断是否存在指定包名
            if(pinfo.get(i).packageName.equalsIgnoreCase(packageName)){
                return true;
            }

        }
        return false;
    }

}








































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































