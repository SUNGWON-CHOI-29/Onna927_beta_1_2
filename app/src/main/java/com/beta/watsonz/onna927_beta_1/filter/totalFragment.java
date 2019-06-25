package com.beta.watsonz.onna927_beta_1.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beta.watsonz.onna927_beta_1.R;

/**
 * Created by watsonz on 2015-12-13.
 */
public class totalFragment extends Fragment {

    TextView place,people,menu,price,time;
    String result;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filter_total, container, false);
        place = (TextView)v.findViewById(R.id.place);
        people = (TextView)v.findViewById(R.id.people);
        menu = (TextView)v.findViewById(R.id.menu);
        price = (TextView)v.findViewById(R.id.price);
        time = (TextView)v.findViewById(R.id.time);

        Setplace("");
        Setpeople("");
        Setmenu("");
        Setprice("");
        Settime("");
        return v;
    }

    public void Setplace(String result)
    {
        place.setText(result);
    }
    public void Setpeople(String result)
    {
        people.setText(result);
    }
    public void Setmenu(String result)
    {
        menu.setText(result);
    }
    public void Setprice(String result)
    {
        price.setText(result);
    }
    public void Settime(String result)
    {
        time.setText(result);
    }

    public String Getplace(){return place.getText().toString().trim();}
    public String Getpeople(){return people.getText().toString().trim();}
    public String Getmenu(){return menu.getText().toString().trim();}
    public String Getprice(){return price.getText().toString().trim();}
    public String Gettime(){return time.getText().toString().trim();}

    public String Getresult(){
        result ="장소 : ";result = result.concat(place.getText().toString());

        result = result.concat("\n인원 : ");
        result = result.concat(people.getText().toString());

        result = result.concat("\n메뉴 :");
        result = result.concat(menu.getText().toString());

        result = result.concat("\n가격 : ");
        result = result.concat(price.getText().toString());

        result = result.concat("\n일시 : ");
        result = result.concat(time.getText().toString());

        return result;
    }
}
