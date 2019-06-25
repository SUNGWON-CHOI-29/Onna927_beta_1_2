package com.beta.watsonz.onna927_beta_1.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.beta.watsonz.onna927_beta_1.R;

/**
 * Created by watsonz on 2015-11-21.
 */
public class FiveFragment extends Fragment {

    totalFragment result;
    String time;
    NumberPicker picker1,picker2,picker3,picker4,picker5;
    TextView pick;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.filter_five, container, false);

        pick = (TextView)v.findViewById(R.id.filter_five_pick);

        picker1 = (NumberPicker)v.findViewById(R.id.numberPicker1);
        picker1.setMinValue(2015);
        picker1.setMaxValue(2020);
        picker1.setWrapSelectorWheel(true);
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                makeresult();
            }
        });
        picker2 = (NumberPicker)v.findViewById(R.id.numberPicker2);
        picker2.setMinValue(1);
        picker2.setMaxValue(12);
        picker2.setWrapSelectorWheel(true);
        picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                makeresult();
            }
        });
        picker3 = (NumberPicker)v.findViewById(R.id.numberPicker3);
        picker3.setMinValue(1);
        picker3.setMaxValue(31);
        picker3.setWrapSelectorWheel(true);
        picker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                makeresult();
            }
        });
        picker4 = (NumberPicker)v.findViewById(R.id.numberPicker4);
        picker4.setMinValue(0);
        picker4.setMaxValue(23);
        picker4.setWrapSelectorWheel(true);
        picker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                makeresult();
            }
        });
        picker5 = (NumberPicker)v.findViewById(R.id.numberPicker5);
        picker5.setMinValue(0);
        picker5.setMaxValue(59);
        picker5.setWrapSelectorWheel(true);
        picker5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                makeresult();
            }
        });
        result = (totalFragment)getActivity().getSupportFragmentManager().findFragmentByTag("total");
        return v;
    }

    public void makeresult()
    {
        time = "";
        time = time.concat(picker1.getValue()+"년 ");
        time = time.concat(picker2.getValue()+"월 ");
        time = time.concat(picker3.getValue()+"일 ");
        time = time.concat(picker4.getValue()+"시 ");
        time = time.concat(picker5.getValue()+"분 ");
        result.Settime(time);
        pick.setText(time);
    }
}
