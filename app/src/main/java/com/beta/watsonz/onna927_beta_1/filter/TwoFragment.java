package com.beta.watsonz.onna927_beta_1.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.beta.watsonz.onna927_beta_1.R;

/**
 * Created by watsonz on 2015-11-21.
 */
public class TwoFragment extends Fragment implements View.OnClickListener {

    TextView people,pick;
    totalFragment result;
    ImageView person1,person2,person3,person4,person5;
    int howmany = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.filter_two, container, false);

        ImageButton button = (ImageButton) v.findViewById(R.id.button1);
        button.setOnClickListener(this);
        ImageButton button2 = (ImageButton) v.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        people = (TextView) v.findViewById(R.id.textView);
        pick = (TextView) v.findViewById(R.id.filter_two_pick);
        result = (totalFragment)getActivity().getSupportFragmentManager().findFragmentByTag("total");
        person1 = (ImageView)v.findViewById(R.id.person1);
        person1.setVisibility(person1.INVISIBLE);
        person2 = (ImageView)v.findViewById(R.id.person2);
        person2.setVisibility(person2.INVISIBLE);
        person3 = (ImageView)v.findViewById(R.id.person3);
        person3.setVisibility(person3.INVISIBLE);
        person4 = (ImageView)v.findViewById(R.id.person4);
        person4.setVisibility(person4.INVISIBLE);
        person5 = (ImageView)v.findViewById(R.id.person5);
        person5.setVisibility(person5.INVISIBLE);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                howmany += 1;
                people.setText(howmany+"명");
                pick.setText(howmany+"명");
                result.Setpeople(howmany + "명");
                showpeople();
                break;
            case R.id.button2:
                if(howmany > 0)howmany -= 1;
                people.setText(howmany+"명");
                pick.setText(howmany+"명");
                result.Setpeople(howmany + "명");
                showpeople();
                break;
        }
    }
    public void showpeople(){
        switch(howmany){
            case 0:
                person4.setVisibility(person4.INVISIBLE);
                break;
            case 1:
                person4.setVisibility(person4.VISIBLE);
                person3.setVisibility(person3.INVISIBLE);
                break;
            case 2:
                person3.setVisibility(person3.VISIBLE);
                person1.setVisibility(person1.INVISIBLE);
                break;
            case 3:
                person1.setVisibility(person1.VISIBLE);
                person2.setVisibility(person2.INVISIBLE);
                break;
            case 4:
                person2.setVisibility(person2.VISIBLE);
                person5.setVisibility(person5.INVISIBLE);
                break;
            case 5:
                person5.setVisibility(person5.VISIBLE);
                break;
            default:
                person1.setVisibility(person1.VISIBLE);
                person2.setVisibility(person2.VISIBLE);
                person3.setVisibility(person3.VISIBLE);
                person4.setVisibility(person4.VISIBLE);
                person5.setVisibility(person5.VISIBLE);
                break;
        }
    }
}

