package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;

public class PopupActivity extends Activity
{
    public static Activity popup_activity;
    WebView m_webview;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        db = new SQLiteHandler(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        layoutParams.flags=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount=0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_popup);
        popup_activity = PopupActivity.this;

        final String target_url = getIntent().getStringExtra("url_result");
        final String target_name = getIntent().getStringExtra("name_result");
        final String user_name = getIntent().getStringExtra("user_name");

        final String pick_place = getIntent().getStringExtra("pick_place");
        final String pick_object = getIntent().getStringExtra("pick_object");
        final String pick_air = getIntent().getStringExtra("pick_air");
        final String pick_people = getIntent().getStringExtra("pick_people");

        Button bt_write = (Button)findViewById(R.id.bt_writeReview);
        bt_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("store_name", target_name);
                extras.putString("user_name", user_name);
                Intent intent = new Intent(getApplicationContext(), reviewWrite.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        Button add_wish = (Button)findViewById(R.id.bt_addWish);
        add_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = db.addWish(target_name,target_url,pick_place,pick_object,pick_air,pick_people);
                if(result)
                    Toast.makeText(getApplicationContext(),"가고싶다 리스트에 추가되었습니다.",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"이미 추가되어 있는 항목입니다.",Toast.LENGTH_SHORT).show();
            }
        });

        //Log.d("send", "url : "+target_url);
        m_webview = (WebView)findViewById(R.id.webview);
        WebSettings set = m_webview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        m_webview.loadUrl(target_url);
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