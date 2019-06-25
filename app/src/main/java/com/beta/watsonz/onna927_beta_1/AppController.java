package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.beta.watsonz.onna927_beta_1.KaKaoHelper.KakaoSDKAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.util.helper.log.Logger;

/**
 * Created by watsonz on 2015-10-03.
 */
public class AppController extends Application{
    private static volatile Activity currentActivity = null;

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    private ImageLoader imageLoader;

    public static Activity getCurrentActivity() {
        Logger.d("++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        AppController.currentActivity = currentActivity;
    }

    public static AppController getGlobalApplicationContext() {
        if(mInstance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.AppController");
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        KakaoSDK.init(new KakaoSDKAdapter());

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            final LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(30);

            @Override
            public void putBitmap(String key, Bitmap value) {
                imageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return imageCache.get(key);
            }
        };

        imageLoader = new ImageLoader(mRequestQueue, imageCache);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        mInstance = null;
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
