package com.beta.watsonz.onna927_beta_1;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.beta.watsonz.onna927_beta_1.KaKaoHelper.KakaoSignupActivity;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;
import com.beta.watsonz.onna927_beta_1.helper.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by watsonz on 2015-07-29.
 */

public class profile_main extends Fragment implements View.OnClickListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    final int REQ_CODE_SELECT_IMAGE=100;
    private TextView txtName;
    private TextView txtEmail;
    private ImageView imgUser;
    BitmapDrawable bitmap;
    Bitmap user_profile;
    private SQLiteHandler db;
    private SessionManager session;

        public profile_main() {

        }

        static profile_main newInstance(int SectionNumber){
            profile_main fragment = new profile_main();
            Bundle args = new Bundle();
            args.putInt("section_number", SectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile,
                    container, false);

            imgUser = (ImageView) rootView.findViewById(R.id.image);
            txtName = (TextView) rootView.findViewById(R.id.name);
            txtEmail = (TextView) rootView.findViewById(R.id.email);
            ImageButton mbtn = (ImageButton) rootView.findViewById(R.id.image_change);
            ImageButton Ibt_history = (ImageButton) rootView.findViewById(R.id.button4);
            ImageButton Ibt_review = (ImageButton) rootView.findViewById(R.id.button2);

            Ibt_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent list_activity = new Intent(getActivity(), purchase_cupon_list_activity.class);
                    getActivity().startActivity(list_activity);
                }
            });
            mbtn.setOnClickListener(this);

            Ibt_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent review_list_activity = new Intent(getActivity(), review_list_Activity.class);
                    getActivity().startActivity(review_list_activity);
                }
            });

            // SqLite database handler
            db = new SQLiteHandler(getActivity());

            // session manager
            session = new SessionManager(getActivity());

            if (!session.isLoggedIn()) {
                logoutUser();
            }

            // Fetching user details from sqlite
            HashMap<String, String> user = db.getUserDetails();

            String name = user.get("name");
            String email = user.get("email");

            // Displaying the user details on the screen
            txtName.setText(name);
            txtEmail.setText(email);
            Resources res = getResources();

            try{
                String imgpath = "/storage/emulated/0/profile/"+name+".jpg";
                if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
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
            // Logout button click event
            return rootView;
        }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.image_change:
                checkPermission();
            break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(getContext(),"Read/Write external storage", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            // 다음 부분은 항상 허용일 경우에 해당이 됩니다.
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    //String name_Str = getImageNameToUri(data.getData());

                    //이미지 데이터를 비트맵으로 받아온다.
                    user_profile = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    imgUser = (ImageView)getActivity().findViewById(R.id.image);

                    //배치해놓은 ImageView에 set
                    imgUser.setImageBitmap(user_profile);
                    if(isExternalStorageWritable())
                        saveBitmaptoJpeg(user_profile,"profile",txtName.getText().toString().trim());
                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Log.d("file location", state);
            return true;
        }
        Log.e("file location", state);
        return false;
    }
    public void saveBitmaptoJpeg(Bitmap bitmap, String folder, String name){
        String ex_storage =Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String foler_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+foler_name;
        //String file_name = name+".jpg";
        File file_path;
        try{
            /*File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), file_name);n
            FileOutputStream out = new FileOutputStream(file);*/
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                if(file_path.mkdirs())
                {
                    Log.d("file created", "");
                }
                else
                {
                    Log.d("file creation error","");
                }
            }
            FileOutputStream out = new FileOutputStream(string_path+file_name);
            Log.d("file location", string_path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            uploadImage(encodedImage,name);
        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
    }
    private void uploadImage(final String image, final String name) {
        // Tag used to cancel the request
        String tag_string_req = "req_Upload";
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Uploading...","Please wait...",false,false);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPLOAD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("upload","Login Response: " + response.toString());
                loading.dismiss();
                /*try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("upload", "Login Error: " + error.getMessage());
                loading.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("image", image);
                params.put("name", name);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
