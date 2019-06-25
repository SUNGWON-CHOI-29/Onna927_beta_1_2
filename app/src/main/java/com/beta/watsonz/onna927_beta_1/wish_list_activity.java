package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.beta.watsonz.onna927_beta_1.helper.CustomAdapter_wish;
import com.beta.watsonz.onna927_beta_1.helper.InfoWish;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by watsonz on 2016-08-26.
 */
public class wish_list_activity extends Activity {
    private SQLiteHandler db;
    private Cursor mCursor;
    private InfoWish mInfoClass;
    private ArrayList<InfoWish> mInfoArray;
    private CustomAdapter_wish mAdapter;
    final String TAG = "my_auction_list";
    //String delete_place;
    private String wish_id;
    HashMap<String, String> getWish = new HashMap<String, String>();

    TextView noitem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        // DB Create and Open
        db = new SQLiteHandler(getApplicationContext());
        mInfoArray = new ArrayList<InfoWish>();
        noitem = (TextView)findViewById(R.id.tv_noitem);
        mInfoArray = db.doWhileCursorToArray_Wish();
        if(mInfoArray.size() == 0)noitem.setText("현재 등록된 항목이 없습니다.");
        else noitem.setText("");

        mAdapter = new CustomAdapter_wish(this, mInfoArray,false);
        ListView mListview = (ListView)findViewById(R.id.listView);

        mListview.setAdapter(mAdapter);
        mListview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getWish = db.getWish(db.wish_id.get(i));
                String user_name = db.getUserDetails().get("name");
                // 다음 액티비티로 넘길 Bundle 데이터를 만든다.
                Bundle extras = new Bundle();

                extras.putString("url_result", getWish.get("url"));
                extras.putString("name_result", getWish.get("store"));
                extras.putString("user_name", user_name);

                extras.putString("pick_place", getWish.get("place"));
                extras.putString("pick_object", getWish.get("object"));
                extras.putString("pick_air", getWish.get("air"));
                extras.putString("pick_people", getWish.get("people"));

                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                // 인텐트를 생성한다.
                // 컨텍스트로 현재 액티비티를, 생성할 액티비티로 ItemClickExampleNextActivity 를 지정한다.
            }
        });
        Button btEdit = (Button)findViewById(R.id.editList);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), wish_edit_activity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mInfoArray.clear();
        mInfoArray = db.doWhileCursorToArray_Wish();
        mAdapter.setArrayList(mInfoArray);
        mAdapter.notifyDataSetChanged();

        if(mInfoArray.size() == 0)noitem.setText("현재 등록된 항목이 없습니다.");
        else noitem.setText("");
    }
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
