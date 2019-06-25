package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by watsonz on 2016-04-06.
 */
public class Cupon_Activity extends Activity{
    String name, store, price, time, mainA, mainB, mainC, sideA, sideB, sideC, drinkA, drinkB, drinkC;
    String aid, store_uid;
    private SQLiteHandler db;
    cupon_list_activity act_cupList = (cupon_list_activity)cupon_list_activity.cup_list;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupon);
        db = new SQLiteHandler(getApplicationContext());
        String temp;
         name = getIntent().getStringExtra("name");
         aid = getIntent().getStringExtra("auction_id");
         store_uid = getIntent().getStringExtra("store_uid");
         store = getIntent().getStringExtra("store");
         price = getIntent().getStringExtra("price");
         time = getIntent().getStringExtra("time");

         mainA = getIntent().getStringExtra("mainA");
         mainB = getIntent().getStringExtra("mainB");
         mainC = getIntent().getStringExtra("mainC");

         sideA = getIntent().getStringExtra("sideA");
         sideB = getIntent().getStringExtra("sideB");
         sideC = getIntent().getStringExtra("sideC");

         drinkA = getIntent().getStringExtra("drinkA");
         drinkB = getIntent().getStringExtra("drinkB");
         drinkC = getIntent().getStringExtra("drinkC");
        //String content = getIntent().getStringExtra("content");

        TextView st_name = (TextView)findViewById(R.id.store_name);
        TextView money = (TextView)findViewById(R.id.money);
        Button bt_store = (Button)findViewById(R.id.bt_store);
        bt_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PopupActivity.class);
                startActivity(intent);
            }
        });

        ImageButton bt_buy = (ImageButton)findViewById(R.id.buy);
        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchase_Cupon(name, aid, store_uid, store, price, time, mainA, mainB, mainC, sideA, sideB, sideC, drinkA, drinkB, drinkC);
            }
        });

        TextView tv_mainA = (TextView)findViewById(R.id.mainA);
        TextView tv_mainB = (TextView)findViewById(R.id.mainB);
        TextView tv_mainC = (TextView)findViewById(R.id.mainC);

        TextView tv_sideA = (TextView)findViewById(R.id.sideA);
        TextView tv_sideB = (TextView)findViewById(R.id.sideB);
        TextView tv_sideC = (TextView)findViewById(R.id.sideC);

        TextView tv_drinkA = (TextView)findViewById(R.id.drinkA);
        TextView tv_drinkB = (TextView)findViewById(R.id.drinkB);
        TextView tv_drinkC = (TextView)findViewById(R.id.drinkC);

        if(Integer.parseInt(mainA) != 0)
        {
            temp = "A - ";
            temp = temp.concat(mainA);
            tv_mainA.setText(temp);
        }else tv_mainA.setHeight(0);
        if(Integer.parseInt(mainB) != 0)
        {
            temp = "B - ";
            temp = temp.concat(mainB);
            tv_mainB.setText(temp);
        }else tv_mainB.setHeight(0);
        if(Integer.parseInt(mainC) != 0)
        {
            temp = "C - ";
            temp = temp.concat(mainC);
            tv_mainC.setText(temp);
        }else tv_mainC.setHeight(0);

        if(Integer.parseInt(sideA) != 0)
        {
            temp = "A - ";
            temp = temp.concat(sideA);
            tv_sideA.setText(temp);
        }else tv_sideA.setHeight(0);
        if(Integer.parseInt(sideB) != 0)
        {
            temp = "B - ";
            temp = temp.concat(sideB);
            tv_sideB.setText(temp);
        }else tv_sideB.setHeight(0);
        if(Integer.parseInt(sideC) != 0)
        {
            temp = "C - ";
            temp = temp.concat(sideC);
            tv_sideC.setText(temp);
        }else tv_sideC.setHeight(0);
        if(Integer.parseInt(drinkA) != 0)
        {
            temp = "A - ";
            temp = temp.concat(drinkA);
            tv_drinkA.setText(temp);
        }else tv_drinkA.setHeight(0);
        if(Integer.parseInt(drinkB) != 0)
        {
            temp = "B - ";
            temp = temp.concat(drinkB);
            tv_drinkB.setText(temp);
        }else tv_drinkB.setHeight(0);
        if(Integer.parseInt(drinkC) != 0)
        {
            temp = "C - ";
            temp = temp.concat(drinkC);
            tv_drinkC.setText(temp);
        }else tv_drinkC.setHeight(0);
        //TextView tx2 = (TextView)findViewById(R.id.tx2);
        //TextView tx3 = (TextView)findViewById(R.id.tx3);

        st_name.setText(store);
        money.setText(price);
        //tx2.setText(content);
        //tx3.setText(price);
    }
    private void purchase_Cupon(final String name, final String aid, final String store_uid, final String store, final String price, final String time, final String mainA, final String mainB, final String mainC,
                                final String SideA, final String SideB, final String SideC, final String DrinkA, final String DrinkB, final String DrinkC) {
        // Tag used to cancel the request
        String tag_string_req = "req_Send_Cupon";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PURCHASE_CUPON, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("PURCHASE_CUPON", "cupon Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    JSONObject my_cupon = jObj.getJSONObject("purchase_cupon");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String id = my_cupon.getString("id");
                        String auction_id = my_cupon.getString("aid");
                        Toast.makeText(getApplicationContext(), "cupon successfully purchased." + id, Toast.LENGTH_LONG).show();
                        deleteAuction(auction_id);
                        act_cupList.finish();
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                    // Inserting row in users table
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
                params.put("name", name);
                params.put("aid", aid);
                params.put("store_uid", store_uid);
                params.put("store", store);
                params.put("price", price);
                params.put("time",time);
                params.put("mainA", mainA);
                params.put("mainB", mainB);
                params.put("mainC", mainC);
                params.put("sideA", SideA);
                params.put("sideB", SideB);
                params.put("sideC", SideC);
                params.put("drinkA", DrinkA);
                params.put("drinkB", DrinkB);
                params.put("drinkC", DrinkC);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void deleteAuction(final String id) {
        // Tag used to cancel the request
        String tag_string_req = "del_Auction";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_AUC_DELETE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    boolean delError = jObj.getBoolean("delError");
                    Log.d("custom adapter", "del Response: " + id);
                    if (!error){
                        db.deleteAuction(id);
                        if(!delError)db.deleteCupon(id);
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Log.d("custom adapter", "del Response: " + errorMsg);
                    }
                    /*mInfoArray.clear();
                    mInfoArray = db.doWhileCursorToArray();
                    mAdapter.setArrayList(mInfoArray);
                    mAdapter.notifyDataSetChanged();*/
                    // Inserting row in users table
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*if(mInfoArray.size() == 0)noitem.setText("현재 등록된 항목이 없습니다.");
                else noitem.setText("");*/
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
                params.put("id", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


