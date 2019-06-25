package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by watsonz on 2016-08-17.
 */
public class reviewWrite extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_writereview);

        final String target_name = getIntent().getStringExtra("store_name");
        final String target_user = getIntent().getStringExtra("user_name");
        final EditText contents = (EditText)findViewById(R.id.reviewContents);
        final RatingBar point = (RatingBar)findViewById(R.id.ratingBar);
        Button confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReview(target_name,
                        target_user,
                        String.valueOf(point.getRating()),
                        contents.getText().toString().trim(),
                        "http://img.tenasia.hankyung.com/webwp_kr/wp-content/uploads/2015/01/2015011215174412079-540x810.jpg");
                finish();
            }
        });
    }
    private void sendReview(final String store_name, final String user_name, final String review_point, final String reviewContents, final String img_url) {
        // Tag used to cancel the request
        String tag_string_req = "req_shake_list";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_WRITE_REVIEW, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("shake", "shake Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                            Toast.makeText(getApplicationContext(),"리뷰 성공",Toast.LENGTH_SHORT).show();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("store", store_name);
                params.put("user_name", user_name);
                params.put("review_point", review_point);
                params.put("reviewContents", reviewContents);
                params.put("img_url", img_url);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
