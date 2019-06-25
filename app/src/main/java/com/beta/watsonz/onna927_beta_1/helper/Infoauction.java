package com.beta.watsonz.onna927_beta_1.helper;

/**
 * Created by watsonz on 2016-02-25.
 */
public class Infoauction{
    public String id;
    public String name;
    public String place;
    public String people;
    public String menu;
    public String price;
    public String time;
    public String created_at;

    public Infoauction(){}

    public Infoauction(String id,String name, String place, String people,
            String menu, String price, String time){
        this.id         = id;
        this.name       = name;
        this.place      = place;
        this.people     = people;
        this.menu       = menu;
        this.price      =  price;
        this.time       = time;

    }
}
