package com.example.android.recyclerviewproject;

import java.util.ArrayList;

public class ServiceItem {

    //class for one service within a program

    private double myHrs;
    private ArrayList<ServiceItem> serviceList;
    private String myProgName;
    private String myDuties; //grab from findViewById(R.id.extra_duties)

    public ServiceItem(double  hours, String prog, String duty){
        myHrs = hours;
       myProgName = prog;
       myDuties = duty;
    }
}
