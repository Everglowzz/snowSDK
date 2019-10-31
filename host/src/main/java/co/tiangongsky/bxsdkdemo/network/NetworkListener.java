package co.tiangongsky.bxsdkdemo.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Author   :byd
 * Date     :2017/9/23
 * Description:
 */

/**
 * 将成功和失败的回调抽取在一起 既是是成功的回调，也是失败的回调
 * @param <T>
 */
public class NetworkListener<T> implements Response.Listener<T>, Response.ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(T response) {

    }
}
