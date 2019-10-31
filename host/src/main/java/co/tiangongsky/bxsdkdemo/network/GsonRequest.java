package co.tiangongsky.bxsdkdemo.network;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Author   :byd
 * Date     :2017/9/23
 * Description:
 */

/**
 * Volley不提供将网络数据解析成一个Java Bean网络请求，自定义一个Gson请求，使用Gson将网络数据解析成一个java bean
 *
 * 泛型T表示要解析成数据类型 java bean的类型
 */
public class GsonRequest<T> extends JsonRequest<T> {

    private static final String TAG = "GsonRequest";

    //要转换成的java bean Class对象，不要写死，是一个变量
    Class<T> mClass;

    private Gson mGson = new Gson();

    /**
     *
     * @param method 请求方法 GET POST PUT .......
     * @param classz java bean的class 对象
     * @param url 请求地址
     * @param requestBody 要上传json字符串
     * @param listener 网络成功的监听器
     * @param errorListener 网络失败的监听器
     */
    public GsonRequest(int method, Class<T> classz, String url, String requestBody, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        mClass = classz;
    }

    /**
     * GET请求 不上传数据requestBody = null
     */
    public GsonRequest(Class<T> classz, String url, Response.Listener listener, Response.ErrorListener errorListener) {
        this(Method.GET, classz, url, null, listener, errorListener);
    }

    /**
     * 将两个监听器组合成一个监听器 NetworkListener既是成功的回调 又是失败的回调
     */
    public GsonRequest(Class<T> classz, String url, NetworkListener listener) {
        this(classz, url, listener, listener);
    }

    /**
     * 解析网络结果成一个java bean, 只有当网络成功之后在去解析，如果这个方法能够被调用到，也就说明
     * 网络请求已经成功  在子线程中执行
     * @param response
     * @return 返回一个解析后的结果，后续Volley回去回调网络成功的监听器
     */
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        Log.d(TAG, "parseNetworkResponse: ");
        byte[] data = response.data;//网络响应的原始数据
        //将字节数组转换成字符串 使用utf-8字符集
        try {
            String resultString = new String(data, PROTOCOL_CHARSET);
            Log.d(TAG, "parseNetworkResponse: " );
            //转换成java bean
/*            CategoryBean categoryBean = 4mGson.fromJson(resultString, CategoryBean.class);
            String title = categoryBean.getData().get(0).getChildren().get(0).getTitle();*/
//            Log.d(TAG, "parseNetworkResponse: " + title);
            //转换成想要的java bean
            T result = mGson.fromJson(resultString, mClass);
            //缓存相关的字段
            Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);//从网络响应中解析出缓存相关的信息
            return Response.success(result, entry);//返回解析后的结果
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
