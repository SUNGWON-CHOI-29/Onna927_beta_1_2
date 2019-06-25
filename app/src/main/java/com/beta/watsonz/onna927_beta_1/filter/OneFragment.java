package com.beta.watsonz.onna927_beta_1.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.beta.watsonz.onna927_beta_1.R;

/**
 * Created by watsonz on 2015-11-21.
 */
public class OneFragment extends Fragment implements View.OnClickListener {

    totalFragment result;
    TextView pick;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.filter_one, container, false);
        ImageButton button = (ImageButton) v.findViewById(R.id.button3);
        button.setOnClickListener(this);
        ImageButton button2 = (ImageButton) v.findViewById(R.id.button4);
        button2.setOnClickListener(this);
        ImageButton button3 = (ImageButton) v.findViewById(R.id.button5);
        button3.setOnClickListener(this);
        ImageButton button4 = (ImageButton) v.findViewById(R.id.button6);
        button4.setOnClickListener(this);
        ImageButton button5 = (ImageButton) v.findViewById(R.id.button7);
        button5.setOnClickListener(this);
        pick = (TextView)v.findViewById(R.id.filter_one_pick);
        result = (totalFragment)getActivity().getSupportFragmentManager().findFragmentByTag("total");
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

           case R.id.button3:
               result.Setplace("홍대");
               pick.setText("홍대");
                break;
            case R.id.button4:
                result.Setplace("이태원");
                pick.setText("이태원");
                break;
            case R.id.button5:
                result.Setplace("대학로");
                pick.setText("대학로");
                break;
            case R.id.button6:
               result.Setplace("강남");
                pick.setText("강남");
                break;
            case R.id.button7:
               result.Setplace("전체선택");
                pick.setText("전체 선택");
                break;

        }
    }
}