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
 * Created by watsonz on 2016-08-26.
 */
public class CustomAdapter_wish extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<InfoWish> infoList;
    private ViewHolder viewHolder;
    private boolean EditVersion;
    public CustomAdapter_wish(Context c , ArrayList<InfoWish> array, boolean isEdit){
        inflater = LayoutInflater.from(c);
        infoList = array;
        EditVersion = isEdit;
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
            if(EditVersion)
                    v = inflater.inflate(R.layout.list_row_edit_wish, null);
            else if(!EditVersion)
                    v = inflater.inflate(R.layout.list_row_wish, null);
            viewHolder.place = (TextView)v.findViewById(R.id.tv_place);
            viewHolder.people = (TextView)v.findViewById(R.id.tv_people);
            viewHolder.menu = (TextView)v.findViewById(R.id.tv_menu);
            viewHolder.time = (TextView)v.findViewById(R.id.tv_time);
            v.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.place.setText(infoList.get(position).store_name);
        viewHolder.people.setText(infoList.get(position).place);
        viewHolder.menu.setText(infoList.get(position).object);
        viewHolder.time.setText(infoList.get(position).air);

        return v;
    }

    public void setArrayList(ArrayList<InfoWish> arrays){
        this.infoList = arrays;
    }

    public ArrayList<InfoWish> getArrayList(){
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
