package com.beta.watsonz.onna927_beta_1.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beta.watsonz.onna927_beta_1.R;

import java.util.ArrayList;

/**
 * Created by watsonz on 2016-02-25.
 */
public class CustomAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<Infoauction> infoList;
    private ViewHolder viewHolder;
    public CustomAdapter(Context c , ArrayList<Infoauction> array){
        inflater = LayoutInflater.from(c);
        infoList = array;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }


    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View v = convertview;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row, null);
            viewHolder.place = (TextView)v.findViewById(R.id.tv_place);
            viewHolder.people = (TextView)v.findViewById(R.id.tv_people);
            viewHolder.menu = (TextView)v.findViewById(R.id.tv_menu);
            viewHolder.time = (TextView)v.findViewById(R.id.tv_time);
            v.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.place.setText(infoList.get(position).place);
        viewHolder.people.setText(infoList.get(position).people);
        viewHolder.menu.setText(infoList.get(position).menu);
        viewHolder.time.setText(infoList.get(position).time);

        return v;
    }

    public void setArrayList(ArrayList<Infoauction> arrays){
        this.infoList = arrays;
    }

    public ArrayList<Infoauction> getArrayList(){
        return infoList;
    }


    /*
     * ViewHolder
     */
    class ViewHolder{
        TextView place;
        TextView people;
        TextView menu;
        TextView time;
    }
}
