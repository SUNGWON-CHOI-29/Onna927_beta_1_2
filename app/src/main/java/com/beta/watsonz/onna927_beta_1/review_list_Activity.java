package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beta.watsonz.onna927_beta_1.helper.CustomAdapter_review;
import com.beta.watsonz.onna927_beta_1.helper.InfoReview;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by watsonz on 2016-06-29.
 */
public class review_list_Activity extends Activity {
    private ArrayList<InfoReview> mInfoArray;
    private CustomAdapter_review mAdapter;
    private SQLiteHandler db;
    final String TAG = "my_review_list";
    HashMap<String, String> getReview = new HashMap<String, String>();
    String user_name;
    TextView noitem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        noitem = (TextView)findViewById(R.id.tv_noitem);

        // DB Create and Open
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        user_name = user.get("name");
        getReviewList(user_name);
        mInfoArray = new ArrayList<InfoReview>();

        mAdapter = new CustomAdapter_review(this, mInfoArray);
        ListView mListview = (ListView)findViewById(R.id.listView);
        mListview.setAdapter(mAdapter);
        mListview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getReview = db.getReview(db.review_id.get(i));

                // 다음 액티비티로 넘길 Bundle 데이터를 만든다.
                Bundle extras = new Bundle();


                extras.putString("id", getReview.get("review_id"));
                extras.putString("store_name", getReview.get("store"));

                // 인텐트를 생성한다.
                // 컨텍스트로 현재 액티비티를, 생성할 액티비티로 ItemClickExampleNextActivity 를 지정한다.
                Intent intent = new Intent(getApplicationContext(), review_Activity.class);
                // 위에서 만든 Bundle을 인텐트에 넣는다.
                intent.putExtras(extras);
                // 액티비티를 생성한다.
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mInfoArray.clear();
        mInfoArray = db.doWhileCursorToArray_Review();
        mAdapter.setArrayList(mInfoArray);
        mAdapter.notifyDataSetChanged();

        if(mInfoArray.size() == 0)noitem.setText("현재 등록된 항목이 없습니다.");
        else noitem.setText("");
    }
    private void getReviewList(final String send_user_name) {
        // Tag used to cancel the request
        String tag_string_req = "req_review_list";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REVIEW_LIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "cupon Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray review = jObj.getJSONArray("review");
                    int size = review.length();

                    for(int i =0; i < size; i++){
                        String id = review.getJSONObject(i).getString("id");
                        String store = review.getJSONObject(i).getString("store");
                        String time = review.getJSONObject(i).getString("time");
                        String dirty = review.getJSONObject(i).getString("dirty");
                        Log.d(TAG, "review Response: " + store + time);
                        db.addReview(id, store, time,dirty);
                    }
                    mInfoArray.clear();
                    mInfoArray = db.doWhileCursorToArray_Review();
                    mAdapter.setArrayList(mInfoArray);
                    mAdapter.notifyDataSetChanged();
                    // Inserting row in users table

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(mInfoArray.size() == 0)noitem.setText("현재 등록된 항목이 없습니다.");
                else noitem.setText("");
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
                params.put("name", send_user_name);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
