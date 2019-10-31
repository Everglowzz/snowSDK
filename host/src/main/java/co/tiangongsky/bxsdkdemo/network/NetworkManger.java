package co.tiangongsky.bxsdkdemo.network;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * 维护一个全局的请求队列
 */
public class NetworkManger {

    private static final int CACHE_SIZE = 5 * 1024 * 1024;//5M缓存

    //全局的请求队列，一个app只维护一个请求队列
    private static RequestQueue sRequestQueue;

    private static ImageLoader sImageLoader;//全局的图片加载器，发送网络请求获取图片

    public static void init(Context context) {
        sRequestQueue = Volley.newRequestQueue(context);
        sImageLoader = new ImageLoader(sRequestQueue, new BitmapLruCache(CACHE_SIZE));

    }

    public static void sendRequest(Request request) {
        sRequestQueue.add(request);//加入全局请求队列
    }

    /**
     * LruCache 一级缓存或者内存缓存
     *
     * key url
     * value bitmap
     */
    private static class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{

        //缓存的最大大小
        public BitmapLruCache(int maxSize) {
            super(maxSize);
        }

        //获取要缓存数据的大小，检查是否爆仓
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();//返回图片的字节数
        }

        /**
         * 从LruCache里面获取对应url的bitmap的缓存
         * @param url
         * @return
         */
        @Override
        public Bitmap getBitmap(String url) {
            return get(url); //hashmap缓存获取对应的图片
        }

        /**
         * 将bitmap存入到缓存
         * @param url
         * @param bitmap
         */
        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }

    public static ImageLoader getImageLoader() {
        return sImageLoader;
    }
}
