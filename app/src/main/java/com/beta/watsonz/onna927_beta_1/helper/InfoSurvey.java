package com.beta.watsonz.onna927_beta_1.helper;

/**
 * Created by watsonz on 2016-06-30.
 */
public class InfoSurvey {
    public String id;
    public String title;
    public String type;
    public String answer1;
    public String answer2;
    public String answer3;
    public String answer4;
    public String answer5;


    public InfoSurvey(){}

    public InfoSurvey(String id,String title, String type,
                      String ans1, String ans2, String ans3, String ans4, String ans5){
        this.id                  = id;
        this.title              = title;
        this.type               = type;
        this.answer1           = ans1;
        this.answer2           = ans2;
        this.answer3           = ans3;
        this.answer4           = ans4;
        this.answer5           = ans5;
    }
}
