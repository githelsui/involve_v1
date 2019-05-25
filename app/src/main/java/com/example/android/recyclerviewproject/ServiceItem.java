package com.example.android.recyclerviewproject;

import java.util.ArrayList;


//class for one service within a program
public class ServiceItem {

    private double myHrs;
    private String myProgName;
    private String myDuties; //grab from findViewById(R.id.extra_duties)

    public ServiceItem(double  hours, String prog, String duty){
        myHrs = hours;
       myProgName = prog;
       myDuties = duty;
    }
}
