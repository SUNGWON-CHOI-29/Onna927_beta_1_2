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
public class ThreeFragment extends Fragment implements View.OnClickListener {

    String menu;
    TextView pick;
    boolean first, second, third, fourth, fifth, sixth, seventh, eight, ninth,all;
    totalFragment result;
    ImageButton button, button1, button2, button3, button4, button5,button6, button7, button8,button9;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.filter_three, container, false);

        button = (ImageButton) v.findViewById(R.id.button);
        button.setOnClickListener(this);
        button1 = (ImageButton) v.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (ImageButton) v.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (ImageButton) v.findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (ImageButton) v.findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = (ImageButton) v.findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = (ImageButton) v.findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = (ImageButton) v.findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = (ImageButton) v.findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9 = (ImageButton) v.findViewById(R.id.button9);
        button9.setOnClickListener(this);

        pick = (TextView)v.findViewById(R.id.filter_three_pick);
        result = (totalFragment)getActivity().getSupportFragmentManager().findFragmentByTag("total");
        first = second = third = fourth = fifth = sixth = seventh = eight = ninth = all = false;
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
               first = !first;
                makeresult();
                button.setSelected(!button.isSelected());
                break;
            case R.id.button1:
                second = !second;
                makeresult();
                button1.setSelected(!button1.isSelected());
                break;
            case R.id.button2:
                third = !third;
                makeresult();
                button2.setSelected(!button2.isSelected());
                break;
            case R.id.button3:
                fourth = !fourth;
                makeresult();
                button3.setSelected(!button3.isSelected());
                break;
            case R.id.button4:
                fifth = !fifth;
                makeresult();
                button4.setSelected(!button4.isSelected());
                break;
            case R.id.button5:
                sixth = !sixth;
                makeresult();
                button5.setSelected(!button5.isSelected());
                break;
            case R.id.button6:
                seventh = !seventh;
                makeresult();
                button6.setSelected(!button6.isSelected());
                break;
            case R.id.button7:
                eight = !eight;
                makeresult();
                button7.setSelected(!button7.isSelected());
                break;
            case R.id.button8:
                ninth = !ninth;
                makeresult();
                button8.setSelected(!button8.isSelected());
                break;
            case R.id.button9:
                all = !all;
                makeresult();
                button9.setSelected(!button9.isSelected());
                break;
        }
    }
    public void makeresult()
    {
        menu = "";
        if(all)menu = menu.concat("전체 선택");
        else {
            if (first) menu = menu.concat(" 분식");
            if (second) menu = menu.concat(" 한식");
            if (third) menu = menu.concat(" 한식기타");
            if (fourth) menu = menu.concat(" 치킨");
            if (fifth) menu = menu.concat(" 피자&파스타");
            if (sixth) menu = menu.concat(" 양식기타");
            if (seventh) menu = menu.concat(" 일식");
            if (eight) menu = menu.concat(" 중식");
            if (ninth) menu = menu.concat(" 세계요리");
        }
        result.Setmenu(menu);
        pick.setText(menu);
    }
}
