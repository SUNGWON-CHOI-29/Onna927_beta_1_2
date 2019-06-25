package com.beta.watsonz.onna927_beta_1.helper;

/**
 * Created by watsonz on 2016-08-26.
 */
public class InfoWish {
    public String id;
    public String store_name;
    public String url;
    public String place;
    public String object;
    public String air;
    public String people;

    public InfoWish(){}

    public InfoWish(String id,String store_name, String url, String place,
                       String object, String air, String people){
        this.id                = id;
        this.store_name       = store_name;
        this.url               = url;
        this.place            = place;
        this.object           = object;
        this.air               =  air;
        this.people            = people;

    }
}
