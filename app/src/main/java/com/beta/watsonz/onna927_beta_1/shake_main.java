package com.beta.watsonz.onna927_beta_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beta.watsonz.onna927_beta_1.filter.shakeFilterPeople;
import com.beta.watsonz.onna927_beta_1.filter.shakeFilterPurpose;
import com.beta.watsonz.onna927_beta_1.filter.shakeFilterAir;
import com.beta.watsonz.onna927_beta_1.filter.shakeFilterPlace;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by watsonz on 2015-07-29.
 */
public class shake_main extends Fragment implements View.OnClickListener{              //쉐이크 프레그먼트 implement
    private static final String TAG = shake_main.class.getSimpleName();
    private SQLiteHandler db;

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 1500;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private static final int FILTER_PLACE = 1;
    private static final int FILTER_PURPOSE = 2;
    private static final int FILTER_AIR = 3;
    private static final int FILTER_PEOPLE = 4;

    SensorManager sm;
    SensorEventListener accL;
    Sensor accSensor;
    AlertDialog.Builder m_alert;
    boolean current_page,pop_up;
    public static boolean loading;
    public static int num_popup;
    Toast m_toast;

    RelativeLayout fm_help;
    FrameLayout fm_title;
    FrameLayout fm_content;

    TranslateAnimation ani;
    TranslateAnimation ani_down;
    TranslateAnimation ani_shake;
    TextView current_place, current_object, current_air, current_people;
    Button btPlace, btPurpose, btAir, btPeople;
    HashMap<String, String[]> result_url_list;
    int url_index;
    String user_name;

    boolean isHelp;
    public shake_main(){
        //퍼블릭 생성
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pop_up = false;
        m_alert = new AlertDialog.Builder(getActivity());
        sm = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);    // SensorManager 인스턴스를 가져옴
        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);    // 가속도 센서
        accL = new accListener();       // 가속도 센서 리스너 인스턴스
        isHelp = false;
    }
    @Override
    public void onStart() {
        super.onStart();
        if(this.getUserVisibleHint()) {
            this.registerSensorListener();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.unregisterSensorListener();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(pop_up)
            pop_up = false;
            //m_toast.show();
        //Toast.makeText(getActivity(), "Bid를 수행합니다", Toast.LENGTH_SHORT).show();
        //sm.registerListener(accL, accSensor, SensorManager.SENSOR_DELAY_NORMAL);    // 가속도 센서 리스너 오브젝트를 등록
    }

    @Override
    public void onPause() {
        super.onPause();
        //sm.unregisterListener(accL);    // unregister orientation listener
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case FILTER_PLACE:
                    if(resultCode != Activity.RESULT_OK){
                        current_place.setText("다시 선택해주세요");
                    }
                    else {
                        String reslut = data.getExtras().getString(shakeFilterPlace.TEXT_RESULT);
                        current_place.setText(reslut);
                    }
                result_url_list.clear();
                break;
            case FILTER_PURPOSE:
                if(resultCode != Activity.RESULT_OK){
                    current_object.setText("다시 선택해주세요");
                }
                else {
                    String reslut = data.getExtras().getString(shakeFilterPurpose.TEXT_RESULT);
                    current_object.setText(reslut);
                }
                result_url_list.clear();
                break;
            case FILTER_AIR:
                if(resultCode != Activity.RESULT_OK){
                    current_air.setText("다시 선택해주세요");
                }
                else {
                    String reslut = data.getExtras().getString(shakeFilterAir.TEXT_RESULT);
                    current_air.setText(reslut);
                }
                result_url_list.clear();
                break;
            case FILTER_PEOPLE:
                if(resultCode != Activity.RESULT_OK){
                    current_people.setText("다시 선택해주세요");
                }
                else {
                    String reslut = data.getExtras().getString(shakeFilterPeople.TEXT_RESULT);
                    current_people.setText(reslut);
                }
                result_url_list.clear();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
    private class accListener implements SensorEventListener {
        public void onSensorChanged(SensorEvent event) {  // 가속도 센서 값이 바뀔때마다 호출됨
            if (current_page) {
                long currentTime = System.currentTimeMillis();
                long gabOfTime = (currentTime - lastTime);
                if (gabOfTime > 100) {
                    lastTime = currentTime;
                    x = event.values[SensorManager.DATA_X];
                    y = event.values[SensorManager.DATA_Y];
                    z = event.values[SensorManager.DATA_Z];

                    speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                    if (speed > SHAKE_THRESHOLD) {
                        Log.d("shake", "onSensorChanged: "+loading);
                        Log.d("shake", "onSensorChanged: num"+num_popup);
                        if(!pop_up)
                        {
                            pop_up = true;
                            activateShake();
                        }
                    }
                    lastX = event.values[DATA_X];
                    lastY = event.values[DATA_Y];
                    lastZ = event.values[DATA_Z];
                }
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // First starts (gets called before everything else)
        if(sm == null) {
            return;
        }

        if(menuVisible) {
            this.registerSensorListener();
        } else {
            this.unregisterSensorListener();
        }
    }

    static shake_main newInstance(int SectionNumber){
        shake_main fragment = new shake_main();
        Bundle args = new Bundle();
        args.putInt("section_number", SectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_shake, container, false);
        db = new SQLiteHandler(getActivity());
        HashMap<String, String> user = db.getUserDetails();

        user_name = user.get("name");
        View toast_layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) rootView.findViewById(R.id.toast_layout));
        TextView tx = (TextView)toast_layout.findViewById(R.id.text);
        m_toast = new Toast(getActivity()); // 토스트 객체 생성
        tx.setText("Bid가 활성화 되었습니다.");
        //m_toast.setGravity(Gravity.CENTER, 0, 0);
        m_toast.setDuration(Toast.LENGTH_SHORT);
        m_toast.setView(toast_layout); // 토스트가 보이는 뷰 설정

        current_place = (TextView)rootView.findViewById(R.id.place_value);
        current_object = (TextView)rootView.findViewById(R.id.purpose_value);
        current_air = (TextView)rootView.findViewById(R.id.air_value);
        current_people = (TextView)rootView.findViewById(R.id.people_value);

        btPlace = (Button)rootView.findViewById(R.id.btPlace);
        btPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHelp == false) {
                    Intent intent = new Intent(getContext(), shakeFilterPlace.class);
                    startActivityForResult(intent, FILTER_PLACE);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                }
            }
        });
        btPurpose = (Button)rootView.findViewById(R.id.btPurpose);
        btPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHelp == false) {
                Intent intent = new Intent(getContext(), shakeFilterPurpose.class);
                startActivityForResult(intent, FILTER_PURPOSE);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
            }
        });
        btAir = (Button)rootView.findViewById(R.id.btAir);
        btAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHelp == false) {
                    Intent intent = new Intent(getContext(), shakeFilterAir.class);
                    startActivityForResult(intent, FILTER_AIR);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                }
            }
        });
        btPeople = (Button)rootView.findViewById(R.id.btPeople);
        btPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHelp == false) {
                    Intent intent = new Intent(getContext(), shakeFilterPeople.class);
                    startActivityForResult(intent, FILTER_PEOPLE);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                }
            }
        });

        ani = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -2.0f);
        ani.setFillAfter(true);
        ani.setDuration(500);

        ani_down = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -2.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        ani_down.setFillAfter(true);
        ani_down.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fm_title.startAnimation(ani_shake);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ani_down.setDuration(500);

        ani_shake = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.1f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        ani_shake.setRepeatMode(Animation.REVERSE);
        ani_shake.setRepeatCount(5);
        ani_shake.setDuration(50);

        result_url_list = new HashMap<String, String[]>();
        fm_help = (RelativeLayout)rootView.findViewById(R.id.fm_help);
        fm_title = (FrameLayout)rootView.findViewById(R.id.frame_title);
        fm_content = (FrameLayout)rootView.findViewById(R.id.frame_content);
        fm_help.setVisibility(fm_help.GONE);

        final Button bt_help = (Button)rootView.findViewById(R.id.btHelp);
        bt_help.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               isHelp = true;
               fm_help.setVisibility(fm_help.VISIBLE);
               bt_help.setVisibility(bt_help.GONE);
               fm_content.startAnimation(ani);
               fm_title.startAnimation(ani);
           }
        });
        Button bt_HelpClose = (Button)rootView.findViewById(R.id.btHelpClose);
        bt_HelpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHelp = false;
                fm_help.setVisibility(fm_help.GONE);
                bt_help.setVisibility(bt_help.VISIBLE);
                fm_content.startAnimation(ani_down);
                fm_title.startAnimation(ani_down);
            }
        });
        fm_title.startAnimation(ani_shake);
        return rootView;
    }
    private void registerSensorListener() {
        current_page = true;
        sm.registerListener(accL, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterSensorListener() {
        current_page = false;
        sm.unregisterListener(accL);    // unregister orientation listener
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
    private void getShakeResult(final String select_place, final String select_object, final String select_air, final String select_people) {
        // Tag used to cancel the request
        String tag_string_req = "req_shake_list";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_SHAKE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("shake", "shake Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        url_index = 0;
                        JSONArray shake = jObj.getJSONArray("shake");
                        int size = shake.length();
                    for(int i =0; i < size; i++){
                        String result_url = shake.getJSONObject(i).getString("url");
                        String result_name = shake.getJSONObject(i).getString("store");

                        //Log.d("shake", "shake Response name: " + result_name);
                        String temp_index = String.valueOf(i);
                        result_url_list.put(temp_index,new String[]{result_url,result_name});
                    }
                       startPopup();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getContext(),
                                "만족스러운 가게가 없습니다.", Toast.LENGTH_SHORT).show();
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
                params.put("place", select_place);
                params.put("objective", select_object);
                params.put("air", select_air);
                params.put("people", select_people);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void startPopup()
    {
        Bundle extras = new Bundle();
        String[] temp_result = result_url_list.get(String.valueOf(url_index));
        String temp_place = current_place.getText().toString().trim();
        String temp_object = current_object.getText().toString().trim();
        String temp_air = current_air.getText().toString().trim();
        String temp_people = current_people.getText().toString().trim();

        extras.putString("url_result", temp_result[0]);
        extras.putString("name_result", temp_result[1]);
        extras.putString("user_name", user_name);

        extras.putString("pick_place", temp_place);
        extras.putString("pick_object", temp_object);
        extras.putString("pick_air", temp_air);
        extras.putString("pick_people", temp_people);

        Intent intent = new Intent(getActivity(), PopupActivity.class);
        intent.putExtras(extras);
        getActivity().startActivity(intent);
        url_index++;
    }
    private void activateShake()
    {
        Log.d(TAG, "activateShake: ");
        String temp_place = current_place.getText().toString().trim();
        String temp_object = current_object.getText().toString().trim();
        String temp_air = current_air.getText().toString().trim();
        String temp_people = current_people.getText().toString().trim();
        if (result_url_list.isEmpty())
            getShakeResult(temp_place, temp_object,temp_air,temp_people);
        else if (url_index < result_url_list.size()) {
            startPopup();
        } else {
            getShakeResult(temp_place, temp_object,temp_air,temp_people);
        }
    }
}
