package com.beta.watsonz.onna927_beta_1.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beta.watsonz.onna927_beta_1.R;

/**
 * Created by watsonz on 2016-08-02.
 */
public class shakeFilterAir extends Activity {
    public static final String TEXT_RESULT = "TextResult";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterair);

        final Button bt_one = (Button)findViewById(R.id.btOne);
        bt_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = bt_one.getText().toString();
                Intent i = new Intent();
                i.putExtra(TEXT_RESULT, result);
                setResult(RESULT_OK, i);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
        });
        final Button bt_two = (Button)findViewById(R.id.btTwo);
        bt_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = bt_two.getText().toString();
                Intent i = new Intent();
                i.putExtra(TEXT_RESULT, result);
                setResult(RESULT_OK, i);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
        });
        final Button bt_three = (Button)findViewById(R.id.btThree);
        bt_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = bt_three.getText().toString();
                Intent i = new Intent();
                i.putExtra(TEXT_RESULT, result);
                setResult(RESULT_OK, i);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

            }
        });
        final Button bt_four = (Button)findViewById(R.id.btFour);
        bt_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = bt_four.getText().toString();
                Intent i = new Intent();
                i.putExtra(TEXT_RESULT, result);
                setResult(RESULT_OK, i);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

            }
        });
        final Button bt_five = (Button)findViewById(R.id.btFive);
        bt_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = bt_five.getText().toString();
                Intent i = new Intent();
                i.putExtra(TEXT_RESULT, result);
                setResult(RESULT_OK, i);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

            }
        });
        final Button bt_six = (Button)findViewById(R.id.btSix);
        bt_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = bt_six.getText().toString();
                Intent i = new Intent();
                i.putExtra(TEXT_RESULT, result);
                setResult(RESULT_OK, i);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

            }
        });
    }
}
