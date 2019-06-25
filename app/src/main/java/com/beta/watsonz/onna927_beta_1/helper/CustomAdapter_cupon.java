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
 * Created by watsonz on 2016-04-06.
 */
public class CustomAdapter_cupon extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<InfoCupon> infoList;
    private ViewHolder viewHolder;
    public CustomAdapter_cupon(Context c , ArrayList<InfoCupon> array){
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
            v = inflater.inflate(R.layout.list_row_cupon, null);
            viewHolder.imguser = (ImageView)v.findViewById(R.id.store_img);
            viewHolder.store = (TextView)v.findViewById(R.id.tv_store);
            viewHolder.time = (TextView)v.findViewById(R.id.tv_time);
            viewHolder.main_num = (TextView)v.findViewById(R.id.tv_main_num);
            viewHolder.side_num = (TextView)v.findViewById(R.id.tv_side_num);
            viewHolder.drink_num = (TextView)v.findViewById(R.id.tv_drink_num);
            v.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)v.getTag();
        }
        Resources res = v.getResources();
        bitmap = (BitmapDrawable)res.getDrawable(R.drawable.user);
        int main_num = Integer.parseInt(infoList.get(position).mainA) + Integer.parseInt(infoList.get(position).mainB) + Integer.parseInt(infoList.get(position).mainC);
        int side_num = Integer.parseInt(infoList.get(position).sideA) + Integer.parseInt(infoList.get(position).sideB) + Integer.parseInt(infoList.get(position).sideC);
        int drink_num = Integer.parseInt(infoList.get(position).drinkA) + Integer.parseInt(infoList.get(position).drinkB) + Integer.parseInt(infoList.get(position).drinkC);

        viewHolder.imguser.setImageDrawable(bitmap);
        viewHolder.store.setText(infoList.get(position).store);
        viewHolder.time.setText(infoList.get(position).time);
        viewHolder.main_num.setText(String.valueOf(main_num));
        viewHolder.side_num.setText(String.valueOf(side_num));
        viewHolder.drink_num.setText(String.valueOf(drink_num));
        return v;
    }

    public void setArrayList(ArrayList<InfoCupon> arrays){
        this.infoList = arrays;
    }

    public ArrayList<InfoCupon> getArrayList(){
        return infoList;
    }


    /*
     * ViewHolder
     */
    class ViewHolder{
        TextView store;
        ImageView imguser;
        TextView time;
        TextView main_num;
        TextView side_num;
        TextView drink_num;
    }


}
