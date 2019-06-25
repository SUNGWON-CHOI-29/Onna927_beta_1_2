package com.beta.watsonz.onna927_beta_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.beta.watsonz.onna927_beta_1.KaKaoHelper.KakaoSignupActivity;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.fivehundredpx.android.blur.BlurringView;

import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;  //페이지 어댑터
    ActionBarDrawerToggle drawerToggle;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;   //viewPager 선언
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private SQLiteHandler db;
    private ImageView imgUser;
    Bitmap user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Button bt_feed = (Button)findViewById(R.id.btFeed);
        bt_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), feedActivity.class);
                startActivity(intent);
            }
        });

        Button bt_wish = (Button)findViewById(R.id.btWish);
        bt_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), wish_list_activity.class);
                startActivity(intent);
            }
        });
        final ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //actionBar.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        //actionBar.setStackedBackgroundDrawable(new ColorDrawable(0x00ffffff));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);

        RelativeLayout drawer_content = (RelativeLayout)findViewById(R.id.drawer_content);
        BlurringView mBlurringView = (BlurringView) findViewById(R.id.blurring_view);
        mBlurringView.setBlurredView(mViewPager);
        drawer_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        SeekBar set_sensor = (SeekBar)findViewById(R.id.seekBar);
        set_sensor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.n
            actionBar.addTab(
                    actionBar.newTab()
                            //.setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setIcon(mSectionsPagerAdapter.getPageIcon(i))
                            .setTabListener(this));
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        db = new SQLiteHandler(this);
        HashMap<String, String> user = db.getUserDetails();
        imgUser = (ImageView)findViewById(R.id.image);

        String name = user.get("name");
        try{
            String imgpath = "/storage/emulated/0/profile/"+name+".jpg";
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                if(KakaoSignupActivity.isKakao)
                {
                    // ImageRequest
                    String imgurl = KakaoSignupActivity.user_img;

                    final ImageRequest imageRequest = new ImageRequest(imgurl,
                            new Response.Listener<Bitmap>()
                            {

                                @Override
                                public void onResponse(Bitmap bitmap)
                                {
                                    // TODO Auto-generated method stub
                                    imgUser.setImageBitmap(bitmap);
                                }
                            },
                            100, 100, Bitmap.Config.ARGB_8888,

                            new Response.ErrorListener()
                            {
                                public void onErrorResponse(VolleyError arg0)
                                {
                                    imgUser.setImageResource(R.drawable.user);
                                    // TODO Auto-generated method stub
                                }
                            });
                    AppController.getInstance().addToRequestQueue(imageRequest);
                }
                else
                {
                    user_profile = BitmapFactory.decodeFile(imgpath);
                    imgUser.setImageBitmap(user_profile);
                }

            }
            else
            {
                imgUser.setImageResource(R.drawable.user);
            }
        }catch(Exception e) {
            //=oast.makeText(getContext(), "load error", Toast.LENGTH_SHORT).show();}
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        /*if (id == R.id.action_approve) {
            Intent intent = new Intent(this, ApproveActivity.class);
            this.startActivity(intent);
            return true;
        }*/
        if(drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.beta.watsonz.onna927_beta_1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.beta.watsonz.onna927_beta_1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            //return PlaceholderFragment.newInstance(position + 1);
            Fragment m_frag = null;
            switch (position) {
                case 0:
                    //m_frag = status_main.newInstance(position +1 );
                    m_frag = shake_main.newInstance(position + 1); // 쉐이크 프레그먼트
                    break;
                case 1:
                    //m_frag = shake_main.newInstance(position +1 ); // 쉐이크 프레그먼트
                    m_frag = profile_main.newInstance(position + 1); // 프로필 프레그먼트
                    break;
                case 2:
                    m_frag = profile_main.newInstance(position + 1); // 프로필 프레그먼트
                    //m_frag = magazine_main.newInstance(position +1 ); // 매거진 프레그먼트
                    break;
                case 3:
                    m_frag = profile_main.newInstance(position + 1); // 프로필 프레그먼트
                    break;
            }
            return m_frag;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    //return getString(R.string.title_section1).toUpperCase(l);
                    return getString(R.string.title_section2).toUpperCase(l);
                case 1:
                    //return getString(R.string.title_section2).toUpperCase(l);
                    return getString(R.string.title_section4).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section4).toUpperCase(l);
                //return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
            }
            return null;
        }

        public int getPageIcon(int position) {
            switch (position) {
                case 0:
                    return R.drawable.shake_set;
                //return R.drawable.status_set;
                case 1:
                    return R.drawable.profile_set;
                //return R.drawable.shake_set;
                case 2:
                    return R.drawable.profile_set;
                //return R.drawable.magazine_set;
                case 3:
                    return R.drawable.profile_set;
            }
            return 0;
        }
    }
}
