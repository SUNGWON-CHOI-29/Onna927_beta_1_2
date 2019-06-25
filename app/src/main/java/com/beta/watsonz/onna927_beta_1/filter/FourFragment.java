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
public class FourFragment extends Fragment{

    String price;
    totalFragment result;
    NumberPicker picker1, picker2;
    TextView pick;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.filter_four, container, false);
        picker1 = (NumberPicker)v.findViewById(R.id.numberPicker2);
        picker1.setMinValue(0);
        picker1.setMaxValue(9);
        picker1.setWrapSelectorWheel(true);
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                makeresult();
            }
        });
        picker2 = (NumberPicker)v.findViewById(R.id.numberPicker3);
        picker2.setMinValue(0);
        picker2.setMaxValue(9);
        picker2.setWrapSelectorWheel(true);
        picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                makeresult();
            }
        });

        pick = (TextView)v.findViewById(R.id.filter_four_pick);
        result = (totalFragment)getActivity().getSupportFragmentManager().findFragmentByTag("total");
        return v;
    }

    public void makeresult()
    {
        price = "";
        price = price.concat(picker1.getValue()+"만 ");
        price = price.concat(picker2.getValue()+"천원");
        result.Setprice(price);
        pick.setText(price);
    }

}
