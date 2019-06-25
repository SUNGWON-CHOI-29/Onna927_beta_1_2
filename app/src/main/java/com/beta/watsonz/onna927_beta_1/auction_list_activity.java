package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beta.watsonz.onna927_beta_1.helper.CustomAdapter;
import com.beta.watsonz.onna927_beta_1.helper.Infoauction;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by watsonz on 2016-02-25.
 */
public class auction_list_activity extends Activity implements AdapterView.OnItemClickListener{
    private SQLiteHandler db;
    private Cursor mCursor;
    private Infoauction mInfoClass;
    private ArrayList<Infoauction> mInfoArray;
    private CustomAdapter mAdapter;
    final String TAG = "my_auction_list";
    //String delete_place;
    private String auc_id;
    int selected;
    HashMap<String, String> getauction = new HashMap<String, String>();
    TextView noitem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_list);



        // DB Create and Open
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        getAuctionList(name);
        mInfoArray = new ArrayList<Infoauction>();
        noitem = (TextView)findViewById(R.id.tv_noitem);
        //mInfoArray = db.doWhileCursorToArray();

        mAdapter = new CustomAdapter(this, mInfoArray);
        ListView mListview = (ListView)findViewById(R.id.listView);
        ImageButton btdelete = (ImageButton)findViewById(R.id.delete);
        btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    auc_id = db.auction_id.get(selected);
                if(auc_id != null){
                    db.auction_id.remove(selected);
                    deleteAuction(auc_id);
                    Log.d("custom adapter", "del Response: " + auc_id);
                }
            }
        });
        mListview.setAdapter(mAdapter);
        mListview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListview.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
    {
        selected = position;
        //TextView tv_place = (TextView)v.findViewById(R.id.tv_place);
        //delete_place = tv_place.getText().toString();
        Log.d(TAG, "select Response: " + position);
    }
    private void getAuctionList(final String user_name) {
        // Tag used to cancel the request
        String tag_string_req = "req_Auction_list";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_AUC_LIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Auction list Response " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONArray auction = jObj.getJSONArray("auction");
                    int size = auction.length();

                    for(int i =0; i < size; i++){
                        String id = auction.getJSONObject(i).getString("id");
                        String name = auction.getJSONObject(i).getString("name");
                        String place = auction.getJSONObject(i).getString("place");
                        String people = auction.getJSONObject(i).getString("people");
                        String menu = auction.getJSONObject(i).getString("menu");
                        String price = auction.getJSONObject(i).getString("price");
                        String time = auction.getJSONObject(i).getString("time");
                        Log.d(TAG, "Auction DB add Response: " + name + place + menu + price + time);
                        db.addAuction(id,name, place, people, menu, price, time);
                    }
                        mInfoArray.clear();
                        mInfoArray = db.doWhileCursorToArray();
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
                params.put("name", user_name);

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
                    mInfoArray.clear();
                    mInfoArray = db.doWhileCursorToArray();
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
                params.put("id", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}

