package com.example.android.recyclerviewproject;

import java.util.ArrayList;


//class for one service within a program
public class ServiceItem {

    private String myDate;
    private double myHrs;
    private String myProgName;
    private String myDuties; //grab data from a large editText view findViewById(R.id.extra_duties)

    public ServiceItem(double  hours, String prog, String duty, String date){
        myHrs = hours;
       myProgName = prog;
       myDuties = duty;
       myDate = date;
    }

    public double getHours(){
        return myHrs;
    }

    public String getDate(){ return myDate; }
}
