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
 * Created by watsonz on 2016-05-29.
 */
public class CustomAdapter_Purchasecupon extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<InfoCupon> infoList;
    private ViewHolder viewHolder;
    public CustomAdapter_Purchasecupon(Context c , ArrayList<InfoCupon> array){
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
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row_purchasecupon, null);
            viewHolder.imguser = (ImageView) v.findViewById(R.id.store_img);
            viewHolder.store = (TextView) v.findViewById(R.id.tv_store);
            viewHolder.time = (TextView) v.findViewById(R.id.tv_time);
            viewHolder.main_num = (TextView) v.findViewById(R.id.tv_main_num);
            viewHolder.side_num = (TextView) v.findViewById(R.id.tv_side_num);
            viewHolder.drink_num = (TextView) v.findViewById(R.id.tv_drink_num);
            viewHolder.mainA = (TextView) v.findViewById(R.id.tv_mainA);
            viewHolder.mainB = (TextView) v.findViewById(R.id.tv_mainB);
            viewHolder.mainC = (TextView) v.findViewById(R.id.tv_mainC);
            viewHolder.sideA = (TextView) v.findViewById(R.id.tv_sideA);
            viewHolder.sideB = (TextView) v.findViewById(R.id.tv_sideB);
            viewHolder.sideC = (TextView) v.findViewById(R.id.tv_sideC);
            viewHolder.drinkA = (TextView) v.findViewById(R.id.tv_drinkA);
            viewHolder.drinkB = (TextView) v.findViewById(R.id.tv_drinkB);
            viewHolder.drinkC = (TextView) v.findViewById(R.id.tv_drinkC);
            v.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        Resources res = v.getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.user);
        int mainAnum = Integer.parseInt(infoList.get(position).mainA);
        int mainBnum = Integer.parseInt(infoList.get(position).mainB);
        int mainCnum = Integer.parseInt(infoList.get(position).mainC);
        int sideAnum = Integer.parseInt(infoList.get(position).sideA);
        int sideBnum = Integer.parseInt(infoList.get(position).sideB);
        int sideCnum = Integer.parseInt(infoList.get(position).sideC);
        int drinkAnum = Integer.parseInt(infoList.get(position).drinkA);
        int drinkBnum = Integer.parseInt(infoList.get(position).drinkB);
        int drinkCnum = Integer.parseInt(infoList.get(position).drinkC);

        int main_num = mainAnum + mainBnum + mainCnum;
        int side_num = sideAnum + sideBnum + sideCnum;
        int drink_num = drinkAnum + drinkBnum + drinkCnum;

        viewHolder.imguser.setImageDrawable(bitmap);
        viewHolder.store.setText(infoList.get(position).store);
        viewHolder.time.setText(infoList.get(position).time);
        viewHolder.main_num.setText(String.valueOf(main_num));
        viewHolder.side_num.setText(String.valueOf(side_num));
        viewHolder.drink_num.setText(String.valueOf(drink_num));

        if (mainAnum > 0) {
            viewHolder.mainA.setText("A - " + mainAnum);
        } else viewHolder.mainA.setText("");
        if (mainBnum > 0) {
            viewHolder.mainB.setText("B - " + mainBnum);
        } else viewHolder.mainB.setText("");
        if (mainCnum > 0){
            viewHolder.mainC.setText("C - " + mainCnum);
          }
        else viewHolder.mainC.setText("");

        if(Integer.parseInt(infoList.get(position).sideA) > 0)
            viewHolder.sideA.setText("A - "+Integer.parseInt(infoList.get(position).sideA));
        else viewHolder.sideA.setHeight(0);
        if(Integer.parseInt(infoList.get(position).sideB) > 0)
            viewHolder.sideB.setText("B - "+Integer.parseInt(infoList.get(position).sideB));
        else viewHolder.sideB.setHeight(0);
        if(Integer.parseInt(infoList.get(position).sideC) > 0)
            viewHolder.sideC.setText("C - "+Integer.parseInt(infoList.get(position).sideC));
        else viewHolder.sideC.setHeight(0);

        if(Integer.parseInt(infoList.get(position).drinkA) > 0)
            viewHolder.drinkA.setText("A - "+Integer.parseInt(infoList.get(position).drinkA));
        else viewHolder.drinkA.setHeight(0);
        if(Integer.parseInt(infoList.get(position).drinkB) > 0)
            viewHolder.drinkB.setText("B - "+Integer.parseInt(infoList.get(position).drinkB));
        else viewHolder.drinkB.setHeight(0);
        if(Integer.parseInt(infoList.get(position).drinkC) > 0)
            viewHolder.drinkC.setText("C - "+Integer.parseInt(infoList.get(position).drinkC));
        else viewHolder.drinkC.setHeight(0);

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
        TextView mainA;
        TextView mainB;
        TextView mainC;
        TextView sideA;
        TextView sideB;
        TextView sideC;
        TextView drinkA;
        TextView drinkB;
        TextView drinkC;
    }


}