package com.beta.watsonz.onna927_beta_1.KaKaoHelper;

/**
 * Created by watsonz on 2016-07-07.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.beta.watsonz.onna927_beta_1.LoginActivity;
import com.beta.watsonz.onna927_beta_1.MainActivity;
import com.beta.watsonz.onna927_beta_1.helper.SQLiteHandler;
import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import com.kakao.util.helper.log.Logger;

public class KakaoSignupActivity extends Activity{
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    public static String user_img;
    public static boolean isKakao;
    private SQLiteHandler db;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SQLiteHandler(getApplicationContext());
        requestMe();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                String name = userProfile.getNickname();
                String email = String.valueOf(userProfile.getId());
                user_img = userProfile.getProfileImagePath();
                isKakao = true;
                // Inserting row in users table
                db.addUser(name, email, "KaKaoId", "");

                Log.d("img", "user img" + user_img);
                Logger.d("UserProfile : " + userProfile);
                redirectMainActivity(); // 로그인 성공시 MainActivity로
            }
        });
    }

    private void redirectMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}
