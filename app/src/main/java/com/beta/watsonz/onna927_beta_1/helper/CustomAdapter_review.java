package com.beta.watsonz.onna927_beta_1.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beta.watsonz.onna927_beta_1.R;

import java.util.ArrayList;

/**
 * Created by watsonz on 2016-06-29.
 */
public class CustomAdapter_review extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<InfoReview> infoList;
    private ViewHolder viewHolder;
    public CustomAdapter_review(Context c , ArrayList<InfoReview> array){
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
        BitmapDrawable bitmap;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row_review, null);
            viewHolder.imgstore = (ImageView)v.findViewById(R.id.store_img);
            viewHolder.store_name = (TextView)v.findViewById(R.id.tv_store);
            viewHolder.time = (TextView)v.findViewById(R.id.tv_time);
            v.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)v.getTag();
        }
        Resources res = v.getResources();
        bitmap = (BitmapDrawable)res.getDrawable(R.drawable.user);

        viewHolder.imgstore.setImageDrawable(bitmap);
        viewHolder.store_name.setText(infoList.get(position).store_name);
        viewHolder.time.setText(infoList.get(position).time);
        return v;
    }

    public void setArrayList(ArrayList<InfoReview> arrays){
        this.infoList = arrays;
    }

    public ArrayList<InfoReview> getArrayList(){
        return infoList;
    }


    /*
     * ViewHolder
     */
    class ViewHolder{
        TextView store_name;
        TextView time;
        ImageView imgstore;
    }
}
