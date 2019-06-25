package com.beta.watsonz.onna927_beta_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.beta.watsonz.onna927_beta_1.filter.FiveFragment;
import com.beta.watsonz.onna927_beta_1.filter.FourFragment;
import com.beta.watsonz.onna927_beta_1.filter.TwoFragment;
import com.beta.watsonz.onna927_beta_1.filter.OneFragment;
import com.beta.watsonz.onna927_beta_1.filter.ThreeFragment;
import com.beta.watsonz.onna927_beta_1.filter.totalFragment;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by watsonz on 2015-07-29.
 */
public class status_main extends Fragment implements View.OnClickListener{

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;


    private SQLiteHandler db;


    private static final int SHAKE_THRESHOLD = 1500;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    totalFragment m_frag;
    String result;
    SensorManager sm;
    SensorEventListener accL;
    Sensor accSensor;
    AlertDialog.Builder m_alert;
    Toast m_toast;
    boolean current_page, pop_up, ready;

    final String TAG = "MainActivity";
    TextView mtext;
    ImageButton bt_oneFragment, bt_twoFragment, bt_threeFragment,
                bt_fourFragment, bt_fiveFragment, bt_close, bt_setup;

    HorizontalScrollView filter;
    ScrollView filter_contents;
    RelativeLayout mother;
    Fragment newFragment = null;
    Fragment newFragment2 = null;
    Fragment newFragment3 = null;
    Fragment newFragment4 = null;
    Fragment newFragment5 = null;
    Fragment newFragment6 = null;


    LinearLayout content_1;
    LinearLayout content_2;
    LinearLayout content_3;
    LinearLayout content_4;
    LinearLayout content_5;
    LinearLayout content_6;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    int current_scroll;

    Intent fab_intent;

    public status_main() {

        }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pop_up = false;
        ready = false;
        m_alert = new AlertDialog.Builder(getActivity());
        sm = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);    // SensorManager 인스턴스를 가져옴
        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);    // 가속도 센서
        accL = new accListener();       // 가속도 센서 리스너 인스턴스
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                getActivity().finish();
            }
            return false;
        }
        return true;
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
        //sm.registerListener(accL, accSensor, SensorManager.SENSOR_DELAY_NORMAL);    // 가속도 센서 리스너 오브젝트를 등록
    }

    @Override
    public void onPause() {
        super.onPause();
        //sm.unregisterListener(accL);    // unregister orientation listener
    }

    private class accListener implements SensorEventListener {
        public void onSensorChanged(SensorEvent event) {  // 가속도 센서 값이 바뀔때마다 호출됨
            if (current_page && ready) {
                long currentTime = System.currentTimeMillis();
                long gabOfTime = (currentTime - lastTime);
                if (gabOfTime > 100) {
                    lastTime = currentTime;
                    x = event.values[SensorManager.DATA_X];
                    y = event.values[SensorManager.DATA_Y];
                    z = event.values[SensorManager.DATA_Z];

                    speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                    if (speed > SHAKE_THRESHOLD && !pop_up) {
                        pop_up = true;
                        // 이벤트발생!!
                        result = m_frag.Getresult();
                        m_alert.setTitle("옥션 정보") //팝업창 타이틀바
                                .setMessage(result) //팝업창 내용
                                .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                        pop_up = false;
                                        filter_end();
                                    }
                                })
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dlg, int sumthin) {
                                        pop_up = false;
                                        HashMap<String, String> user = db.getUserDetails();
                                        String name = user.get("name");
                                        String uid = user.get("uid");
                                        registerAuction(name,uid,m_frag.Getplace(),m_frag.Getpeople(),m_frag.Getmenu(),
                                                m_frag.Getprice(),m_frag.Gettime());
                                        filter_end();
                                        m_toast.show();
                                    }
                                })
                                .setCancelable(false)
                                .show(); // 팝업창 보여줌
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
        static status_main newInstance(int SectionNumber){
            status_main fragment = new status_main();
            Bundle args = new Bundle();
            args.putInt("section_number", SectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_status,
                    container, false);

            bt_oneFragment = (ImageButton) rootView.findViewById(R.id.Button);
            bt_oneFragment.setOnClickListener(this);
            bt_oneFragment.setSelected(true);
            bt_twoFragment = (ImageButton) rootView.findViewById(R.id.Button2);
            bt_twoFragment.setOnClickListener(this);
            bt_threeFragment = (ImageButton) rootView.findViewById(R.id.Button3);
            bt_threeFragment.setOnClickListener(this);
            bt_fourFragment = (ImageButton) rootView.findViewById(R.id.Button4);
            bt_fourFragment.setOnClickListener(this);
            bt_fiveFragment = (ImageButton) rootView.findViewById(R.id.Button5);
            bt_fiveFragment.setOnClickListener(this);
            bt_close = (ImageButton) rootView.findViewById(R.id.Button6);
            bt_close.setOnClickListener(this);

            bt_setup = (ImageButton) rootView.findViewById(R.id.SetUp);
            bt_setup.setOnClickListener(this);

            mtext = (TextView)rootView.findViewById(R.id.guide);
            mtext.setText("상단의 버튼을 눌러 필터를 입력해주세요");

            mother = (RelativeLayout) rootView.findViewById(R.id.motherlayout);
            filter = (HorizontalScrollView) rootView.findViewById(R.id.filterscroll);
            filter.setVisibility(filter.GONE);

            filter_contents = (ScrollView) rootView.findViewById(R.id.contents);
            filter_contents.setVisibility(filter_contents.GONE);
            filter_contents.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    current_scroll = filter_contents.getScrollY();
                    changeScrollState();
                    return false;
                }
            });

            content_1 = (LinearLayout) rootView.findViewById(R.id.ll_fragment);
            content_2 = (LinearLayout) rootView.findViewById(R.id.l2_fragment);
            content_3 = (LinearLayout) rootView.findViewById(R.id.l3_fragment);
            content_4 = (LinearLayout) rootView.findViewById(R.id.l4_fragment);
            content_5 = (LinearLayout) rootView.findViewById(R.id.l5_fragment);
            content_6 = (LinearLayout) rootView.findViewById(R.id.l6_fragment);

            fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
            fab1 = (FloatingActionButton)rootView.findViewById(R.id.fab1);
            fab2 = (FloatingActionButton)rootView.findViewById(R.id.fab2);

            fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
            fab_close = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
            rotate_forward = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_forward);
            rotate_backward = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_backward);
            fab.setOnClickListener(this);
            fab1.setOnClickListener(this);
            fab2.setOnClickListener(this);

            View toast_layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) rootView.findViewById(R.id.toast_layout));
            TextView tx = (TextView)toast_layout.findViewById(R.id.text);
            m_toast = new Toast(getActivity()); // 토스트 객체 생성
            tx.setText("옥션이 활성화 되었습니다.");
            //m_toast.setGravity(Gravity.CENTER, 0, 0);
            m_toast.setDuration(Toast.LENGTH_SHORT);
            m_toast.setView(toast_layout); // 토스트가 보이는 뷰 설정

            db = new SQLiteHandler(getActivity());

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                getActivity().startService(intent);    //서비스 실행
            }
            return rootView;
        }
    public void changeScrollState(){
        if(current_scroll > content_1.getTop()){
            bt_oneFragment.setSelected(true);
            bt_twoFragment.setSelected(false);
            bt_threeFragment.setSelected(false);
            bt_fourFragment.setSelected(false);
            bt_fiveFragment.setSelected(false);
            bt_close.setSelected(false);
        }
        if(current_scroll > content_2.getTop()){
            bt_oneFragment.setSelected(false);
            bt_twoFragment.setSelected(true);
            bt_threeFragment.setSelected(false);
            bt_fourFragment.setSelected(false);
            bt_fiveFragment.setSelected(false);
            bt_close.setSelected(false);
        }
        if(current_scroll > content_3.getTop()){
            bt_oneFragment.setSelected(false);
            bt_twoFragment.setSelected(false);
            bt_threeFragment.setSelected(true);
            bt_fourFragment.setSelected(false);
            bt_fiveFragment.setSelected(false);
            bt_close.setSelected(false);
        }
        if(current_scroll > content_4.getTop()){
            filter_contents.smoothScrollTo(0, content_4.getTop());
            bt_oneFragment.setSelected(false);
            bt_twoFragment.setSelected(false);
            bt_threeFragment.setSelected(false);
            bt_fourFragment.setSelected(true);
            bt_fiveFragment.setSelected(false);
            bt_close.setSelected(false);
        }
        if(current_scroll > content_5.getTop()){
            filter_contents.smoothScrollTo(0, content_5.getTop());
            bt_oneFragment.setSelected(false);
            bt_twoFragment.setSelected(false);
            bt_threeFragment.setSelected(false);
            bt_fourFragment.setSelected(false);
            bt_fiveFragment.setSelected(true);
            bt_close.setSelected(false);
        }
        if(current_scroll > content_6.getTop()){
            filter_contents.smoothScrollTo(0, content_6.getTop());
            bt_oneFragment.setSelected(false);
            bt_twoFragment.setSelected(false);
            bt_threeFragment.setSelected(false);
            bt_fourFragment.setSelected(false);
            bt_fiveFragment.setSelected(false);
            bt_close.setSelected(true);
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button:
                Log.d(TAG, "Button ");
                filter_contents.smoothScrollTo(0, content_1.getTop());
                bt_oneFragment.setSelected(true);
                bt_twoFragment.setSelected(false);
                bt_threeFragment.setSelected(false);
                bt_fourFragment.setSelected(false);
                bt_fiveFragment.setSelected(false);
                bt_close.setSelected(false);
                break;
            case R.id.Button2:
                Log.d(TAG, "Button2 ");
                filter_contents.smoothScrollTo(0, content_2.getTop());
                bt_oneFragment.setSelected(false);
                bt_twoFragment.setSelected(true);
                bt_threeFragment.setSelected(false);
                bt_fourFragment.setSelected(false);
                bt_fiveFragment.setSelected(false);
                bt_close.setSelected(false);
                break;
            case R.id.Button3:
                Log.d(TAG, "Button3 ");
                filter_contents.smoothScrollTo(0, content_3.getTop());
                bt_oneFragment.setSelected(false);
                bt_twoFragment.setSelected(false);
                bt_threeFragment.setSelected(true);
                bt_fourFragment.setSelected(false);
                bt_fiveFragment.setSelected(false);
                bt_close.setSelected(false);
                break;
            case R.id.Button4:
                Log.d(TAG, "Button4 ");
                filter_contents.smoothScrollTo(0, content_4.getTop());
                bt_oneFragment.setSelected(false);
                bt_twoFragment.setSelected(false);
                bt_threeFragment.setSelected(false);
                bt_fourFragment.setSelected(true);
                bt_fiveFragment.setSelected(false);
                bt_close.setSelected(false);
                break;
            case R.id.Button5:
                Log.d(TAG, "Button5 ");
                filter_contents.smoothScrollTo(0, content_5.getTop());
                bt_oneFragment.setSelected(false);
                bt_twoFragment.setSelected(false);
                bt_threeFragment.setSelected(false);
                bt_fourFragment.setSelected(false);
                bt_fiveFragment.setSelected(true);
                bt_close.setSelected(false);
                break;
            case R.id.Button6:
                Log.d(TAG, "Button6 ");
                filter_contents.smoothScrollTo(0, content_6.getTop());
                bt_oneFragment.setSelected(false);
                bt_twoFragment.setSelected(false);
                bt_threeFragment.setSelected(false);
                bt_fourFragment.setSelected(false);
                bt_fiveFragment.setSelected(false);
                bt_close.setSelected(true);
                break;
            case R.id.SetUp:
                Log.d(TAG, "SetUp ");
                filter_start();
                break;
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                fab_intent = new Intent(getActivity(), cupon_list_activity.class);
                getActivity().startActivity(fab_intent);
                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab2:
                fab_intent = new Intent(getActivity(), auction_list_activity.class);
                getActivity().startActivity(fab_intent);
                Log.d("Raj", "Fab 2");
                break;
        }
    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }

    }
    public void filter_start()
    {
        fragmentSetUp();
        m_frag = (totalFragment)getActivity().getSupportFragmentManager().findFragmentByTag("total");
        fab.hide();
        bt_setup.setVisibility(bt_setup.GONE);
        filter.setVisibility(filter.VISIBLE);
        content_6.setVisibility(filter.VISIBLE);
        filter_contents.setVisibility(filter_contents.VISIBLE);
        mtext.setVisibility(mtext.GONE);
        ready = true;
    }
    public void filter_end()
    {
        fragmentRemove();
        filter.setVisibility(filter.GONE);
        filter_contents.setVisibility(filter_contents.GONE);
        bt_setup.setVisibility(bt_setup.VISIBLE);
        mtext.setVisibility(mtext.VISIBLE);
        fab.show();
        ready = false;
    }
    public void fragmentSetUp() {
        Log.d(TAG, "fragmentSet ");
        newFragment = new OneFragment();
        newFragment2 = new TwoFragment();
        newFragment3 = new ThreeFragment();
        newFragment4 = new FourFragment();
        newFragment5 = new FiveFragment();
        newFragment6 = new totalFragment();

        // setUp all fragment

        final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.ll_fragment, newFragment,"place");
        transaction.replace(R.id.l2_fragment, newFragment2,"people");
        transaction.replace(R.id.l3_fragment, newFragment3,"menu");
        transaction.replace(R.id.l4_fragment, newFragment4,"price");
        transaction.replace(R.id.l5_fragment, newFragment5,"time");
        transaction.replace(R.id.l6_fragment, newFragment6,"total");
        //transaction.add(new totalFragment(),"tag");

        // Commit the transaction
        transaction.commit();
        getActivity().getSupportFragmentManager().executePendingTransactions();

    }

    public void fragmentRemove(){
        final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();

        transaction.remove(newFragment);
        transaction.remove(newFragment2);
        transaction.remove(newFragment3);
        transaction.remove(newFragment4);
        transaction.remove(newFragment5);
        transaction.remove(newFragment6);

        transaction.commit();
        getActivity().getSupportFragmentManager().executePendingTransactions();
    }
    private void registerSensorListener() {
        current_page = true;
        sm.registerListener(accL, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterSensorListener() {
        current_page = false;
        sm.unregisterListener(accL);    // unregister orientation listener
    }
    private void registerAuction(final String name, final String uid, final String inputplace,
                              final String people,final String menu, final String price,
                              final String time) {
        // Tag used to cancel the request
        String tag_string_req = "req_Auction";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_AUC_REGI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Auction Add Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject auction = jObj.getJSONObject("auction");
                    String uid = auction.getString("id");
                    String name = auction.getString("name");
                    String place = auction.getString("place");
                    String people = auction.getString("people");
                    String menu = auction.getString("menu");
                    String price = auction.getString("price");
                    String time = auction.getString("time");

                    // Inserting row in users table
                    db.addAuction(uid, name, place, people, menu, price, time);
                    pushAuction(inputplace);
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
                params.put("uid", uid);
                params.put("place", inputplace);
                params.put("people", people);
                params.put("menu", menu);
                params.put("price", price);
                params.put("time", time);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void pushAuction(final String place) {
        // Tag used to cancel the request
        String tag_string_req = "req_Auction";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PUSH_AUCTION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "push cnt: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "push Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("place", place);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
