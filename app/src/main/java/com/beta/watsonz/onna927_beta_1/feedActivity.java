package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by watsonz on 2016-08-18.
 */
public class feedActivity extends Activity{
    WebView m_webview;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //Log.d("send", "url : "+target_url);
        m_webview = (WebView)findViewById(R.id.webview);
        WebSettings set = m_webview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        m_webview.loadUrl("http://onna927.godohosting.com/newsfeed/");
        m_webview.setWebViewClient(new WebClient()); // 응룡프로그램에서 직접 url 처리
    }
    class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url){
            super.onPageFinished(view, url);
            shake_main.loading = false;
        }
    }
}
