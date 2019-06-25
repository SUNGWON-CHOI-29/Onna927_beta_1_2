package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beta.watsonz.onna927_beta_1.helper.InfoSurvey;
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
public class review_Activity extends Activity {
    private SQLiteHandler db;
    SeekBar seekbar;
    TextView tv_title;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    RadioGroup rg;
    RelativeLayout Rl_evalue;
    public ArrayList<String> answerList = new ArrayList<String>();
    ArrayList<InfoSurvey> mInfoArray;
    InfoSurvey mInfoClass;
    String review_id, store_name;
    String total_ans;
    int survey_size;
    RatingBar rate;
    EditText short_comment;
    TextView comment_size;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        db = new SQLiteHandler(getApplicationContext());
        review_id = getIntent().getStringExtra("id");
        store_name = getIntent().getStringExtra("store_name");

        Rl_evalue = (RelativeLayout)findViewById(R.id.evalue);
        Resources res = getResources();
        BitmapDrawable bitmap = (BitmapDrawable)res.getDrawable(R.drawable.user);
        ImageView store_img = (ImageView)findViewById(R.id.store_img);
        store_img.setImageDrawable(bitmap);
        TextView tv_stName = (TextView)findViewById(R.id.store_name);
        tv_stName.setText(store_name);
        rate = (RatingBar)findViewById(R.id.evalue_bar);
        comment_size = (TextView)findViewById(R.id.textsize);
        short_comment = (EditText)findViewById(R.id.comment);
        short_comment.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            comment_size.setText(short_comment.length()+" / 20");
            }
            public void afterTextChanged(Editable s) {
            }
        });

        tv_title = (TextView)findViewById(R.id.survey_title);
        seekbar = (SeekBar)findViewById(R.id.seekbar);
        seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        seekbar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        ImageButton next = (ImageButton)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextPage();
            }
        });
        ImageButton prev = (ImageButton)findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrevPage();
            }
        });
        rg = (RadioGroup)findViewById(R.id.radioGroup);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeAnswer(seekbar.getProgress(),1);
                getNextPage();
            }
        });
        rb2 = (RadioButton)findViewById(R.id.radioButton2);
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeAnswer(seekbar.getProgress(),2);
                getNextPage();
            }
        });
        rb3 = (RadioButton)findViewById(R.id.radioButton3);
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeAnswer(seekbar.getProgress(),3);
                getNextPage();
            }
        });
        rb4 = (RadioButton)findViewById(R.id.radioButton4);
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeAnswer(seekbar.getProgress(),4);
                getNextPage();
            }
        });
        rb5 = (RadioButton)findViewById(R.id.radioButton5);
        rb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeAnswer(seekbar.getProgress(), 5);
                getNextPage();
            }
        });
        getSurveyFromServer();
    }
    private void setSurveyContent(int index)
    {
        tv_title.setText(mInfoArray.get(index).title);

        rb1.setText(mInfoArray.get(index).answer1);
        rb2.setText(mInfoArray.get(index).answer2);
        rb3.setText(mInfoArray.get(index).answer3);
        rb4.setText(mInfoArray.get(index).answer4);
        rb5.setText(mInfoArray.get(index).answer5);
    }
    private void storeAnswer(int cur_page, int index)
    {
        Toast.makeText(getApplicationContext(),"Page = "+cur_page+" answer is "+index,Toast.LENGTH_SHORT).show();
        String temp = String.valueOf(index);
        answerList.add(cur_page, temp);
    }
    private void getcheck(int index){
        switch (index){
            case 1:
                rg.check(R.id.radioButton);
                break;
            case 2:
                rg.check(R.id.radioButton2);
                break;
            case 3:
                rg.check(R.id.radioButton3);
                break;
            case 4:
                rg.check(R.id.radioButton4);
                break;
            case 5:
                rg.check(R.id.radioButton5);
                break;
        }
    }
    private void getSurveyFromServer() {
        // Tag used to cancel the request
        String tag_string_req = "req_servey_item";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_SURVEY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("survey", "getServey Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONArray survey = jObj.getJSONArray("survey");
                    survey_size = survey.length();
                    mInfoArray = new ArrayList<InfoSurvey>();
                    for(int i =0; i < survey_size; i++){
                        String id = survey.getJSONObject(i).getString("id");
                        String title = survey.getJSONObject(i).getString("Title");
                        String type = survey.getJSONObject(i).getString("Type");
                        String ans1 = survey.getJSONObject(i).getString("answer1");
                        String ans2 = survey.getJSONObject(i).getString("answer2");
                        String ans3 = survey.getJSONObject(i).getString("answer3");
                        String ans4 = survey.getJSONObject(i).getString("answer4");
                        String ans5 = survey.getJSONObject(i).getString("answer5");
                        Log.d("survey", "getServey Response: " + title + type + ans1);
                        mInfoClass = new InfoSurvey(id,title,type,ans1,ans2,ans3,ans4,ans5);
                        mInfoArray.add(mInfoClass);
                    }
                    Log.d("survey", "getServey size: " + survey_size);
                    seekbar.setMax(survey_size);
                    setSurveyContent(0);
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

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void setSurvey(final String id, final String survey, final String comment, final String rate) {
        // Tag used to cancel the request
        String tag_string_req = "req_survey_store";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SET_SURVEY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        db.setReviewDirty(id);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("id",id);
                params.put("survey",survey);
                params.put("comment",comment);
                params.put("rate",rate);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void getNextPage()
    {
        int value,current;
        current = seekbar.getProgress();
        Log.d("result Ans","size = " + survey_size + "answer size = " +  answerList.size() + "current = "+current);
        if (current+1 == survey_size && answerList.size() > current) {
            seekbar.incrementProgressBy(1);
            rg.setVisibility(rg.GONE);
            Rl_evalue.setVisibility(Rl_evalue.VISIBLE);
            tv_title.setText("별점 및 간단한 리뷰를 남겨주세요");
            Toast.makeText(getApplicationContext(), "설문 끝", Toast.LENGTH_SHORT).show();
            total_ans = "";
            for(int i = 0; i < answerList.size(); i++)
                total_ans = total_ans.concat(answerList.get(i));
            Log.d("result Ans",total_ans);

        }
        else if(current == survey_size){
            //서버 전송
            float numStar = rate.getRating();
            int sizeString = short_comment.length();
            Log.d("result addtionalSurvey","star = "+numStar+" size = "+sizeString);
            if(numStar > 0 && sizeString  > 0)
            {
                String comment = short_comment.getText().toString().trim();
                String rate =  String.valueOf(numStar);
                String survey = total_ans;
                Log.d("setSurvey Value", "star = " + rate + " survey = " + survey+"comment = " + comment+" id = "+review_id);
                Toast.makeText(getApplicationContext(), "설문내용이 서버로 전송됩니다.", Toast.LENGTH_SHORT).show();
                setSurvey(review_id, survey, comment,rate);
            }
            else Toast.makeText(getApplicationContext(), "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            if(answerList.size() > current) {
                rg.clearCheck();
                seekbar.incrementProgressBy(1);
                current = seekbar.getProgress();
                setSurveyContent(current);
                if(answerList.size() > current) {
                    value = Integer.parseInt(answerList.get(current));
                    getcheck(value);
                    Log.d("radio", "check = " + value);
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "답변 선택을 먼저 해주세요", Toast.LENGTH_SHORT).show();
            }
        }
        Log.d("seek bar", "progress = " + seekbar.getProgress());
    }
    private void getPrevPage()
    {
        rg.setVisibility(rg.VISIBLE);
        Rl_evalue.setVisibility(Rl_evalue.GONE);
        rg.clearCheck();
        int value;
        seekbar.incrementProgressBy(-1);
        int current = seekbar.getProgress();
        setSurveyContent(current);
        if(answerList.size() > current) {
            value = Integer.parseInt(answerList.get(current));
            getcheck(value);
            Log.d("radio", "check = " + value);
        }
    }
}
