package com.example.booklistapp;

import java.util.ArrayList;

public class Books {
    private String ms1;
    private String ms2;
    private String ms3;
    private String ms4;
    private String ms5;
    private String buy;
    private String rate;

    public Books(String s1,String s2,String s3,String s4,String S5,String S6,String S7)
    {
        ms1=s1;
        ms2=s2;
        ms3=s3;
        ms4=s4;
        ms5=S5;
        buy=S6;
        rate=S7;
    }

    public String getMs1() {
        return ms1;
    }
    public String getMs2() {
        return ms2;
    }
    public String getMs3() {
        return ms3;
    }
    public String getMs4() {
        return ms4;
    }
    public String getMs5() {
        return ms5;
    }
    public String getBuy() {
        return buy;
    }
    public String getRate() {
        return rate;
    }


}