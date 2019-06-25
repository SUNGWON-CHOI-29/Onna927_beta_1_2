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
import com.beta.watsonz.onna927_beta_1.helper.CustomAdapter_cupon;
import com.beta.watsonz.onna927_beta_1.helper.InfoCupon;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by watsonz on 2016-04-06.
 */
public class cupon_list_activity extends Activity{
    private SQLiteHandler db;
    private ArrayList<InfoCupon> mInfoArray;
    private CustomAdapter_cupon mAdapter;
    final String TAG = "my_auction_list";
    String user_name;
    HashMap<String, String> getcupon = new HashMap<String, String>();
    TextView noitem;
    public static Activity cup_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupon_list);
        cup_list = cupon_list_activity.this;

        // DB Create and Open
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        user_name = user.get("name");
        getCuponList(user_name);
        mInfoArray = new ArrayList<InfoCupon>();
        noitem = (TextView)findViewById(R.id.tv_noitem);
        mAdapter = new CustomAdapter_cupon(this, mInfoArray);
                ListView mListview = (ListView)findViewById(R.id.listView);
                mListview.setAdapter(mAdapter);
                mListview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        getcupon = db.getCupon(db.cupon_id.get(i));

                        // 다음 액티비티로 넘길 Bundle 데이터를 만든다.
                        Bundle extras = new Bundle();
                        extras.putString("name", user_name);
                        extras.putString("auction_id", getcupon.get("auction_id"));
                        extras.putString("store_uid", getcupon.get("store_uid"));
                        extras.putString("store", getcupon.get("store"));
                        extras.putString("price", getcupon.get("price"));
                        extras.putString("time", getcupon.get("time"));
                        extras.putString("mainA", getcupon.get("mainA"));
                        extras.putString("mainB", getcupon.get("mainB"));
                        extras.putString("mainC", getcupon.get("mainC"));
                        extras.putString("sideA", getcupon.get("sideA"));
                        extras.putString("sideB", getcupon.get("sideB"));
                        extras.putString("sideC", getcupon.get("sideC"));
                        extras.putString("drinkA", getcupon.get("drinkA"));
                        extras.putString("drinkB", getcupon.get("drinkB"));
                        extras.putString("drinkC", getcupon.get("drinkC"));


                // 인텐트를 생성한다.
                // 컨텍스트로 현재 액티비티를, 생성할 액티비티로 ItemClickExampleNextActivity 를 지정한다.
                Intent intent = new Intent(getApplicationContext(), Cupon_Activity.class);
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
    private void getCuponList(final String send_user_name) {
        // Tag used to cancel the request
        String tag_string_req = "req_cupon_list";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CUP_LIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "cupon Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONArray cupon = jObj.getJSONArray("cupon");
                    int size = cupon.length();

                    for(int i =0; i < size; i++){
                        String id = cupon.getJSONObject(i).getString("id");
                        String aid = cupon.getJSONObject(i).getString("aid");
                        String ownerid = cupon.getJSONObject(i).getString("ownerid");
                        String store = cupon.getJSONObject(i).getString("store");
                        String price = cupon.getJSONObject(i).getString("price");
                        String time = cupon.getJSONObject(i).getString("time");
                        String mainA = cupon.getJSONObject(i).getString("mainA");
                        String mainB = cupon.getJSONObject(i).getString("mainB");
                        String mainC = cupon.getJSONObject(i).getString("mainC");
                        String sideA = cupon.getJSONObject(i).getString("sideA");
                        String sideB = cupon.getJSONObject(i).getString("sideB");
                        String sideC = cupon.getJSONObject(i).getString("sideC");
                        String drinkA = cupon.getJSONObject(i).getString("drinkA");
                        String drinkB = cupon.getJSONObject(i).getString("drinkB");
                        String drinkC = cupon.getJSONObject(i).getString("drinkC");
                        Log.d(TAG, "cupon Response: " + store + price );
                        db.addCupon(id, aid,ownerid,store, price, time,mainA, mainB, mainC,
                                sideA, sideB, sideC, drinkA, drinkB, drinkC);
                    }
                    mInfoArray.clear();
                    mInfoArray = db.doWhileCursorToArray_cupon();
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
