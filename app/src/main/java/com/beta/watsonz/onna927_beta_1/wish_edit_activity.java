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

import com.beta.watsonz.onna927_beta_1.helper.CustomAdapter_wish;
import com.beta.watsonz.onna927_beta_1.helper.InfoWish;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;

import java.util.ArrayList;

/**
 * Created by watsonz on 2016-08-26.
 */
public class wish_edit_activity extends Activity implements AdapterView.OnItemClickListener{
private SQLiteHandler db;
private Cursor mCursor;
private InfoWish mInfoClass;
private ArrayList<InfoWish> mInfoArray;
private CustomAdapter_wish mAdapter;
final String TAG = "my_auction_list";
//String delete_place;
private String wish_id;
        int selected;
        TextView noitem;

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_edit);

        // DB Create and Open
        db = new SQLiteHandler(getApplicationContext());
        mInfoArray = new ArrayList<InfoWish>();
        noitem = (TextView)findViewById(R.id.tv_noitem);

        mInfoArray = db.doWhileCursorToArray_Wish();
        if(mInfoArray.size() == 0)noitem.setText("현재 등록된 항목이 없습니다.");
        else noitem.setText("");
        //mInfoArray = db.doWhileCursorToArray();

        mAdapter = new CustomAdapter_wish(this, mInfoArray,true);
        ListView mListview = (ListView)findViewById(R.id.listView);
        ImageButton btdelete = (ImageButton)findViewById(R.id.delete);
        btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wish_id = db.wish_id.get(selected);
                if(wish_id != null){
                db.wish_id.remove(selected);
                db.deleteWish(wish_id);
                Log.d("custom adapter", "del Response: " + wish_id);

                mInfoArray.clear();
                mInfoArray = db.doWhileCursorToArray_Wish();
                mAdapter.setArrayList(mInfoArray);
                mAdapter.notifyDataSetChanged();

                if(mInfoArray.size() == 0)noitem.setText("현재 등록된 항목이 없습니다.");
                else noitem.setText("");
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
}
