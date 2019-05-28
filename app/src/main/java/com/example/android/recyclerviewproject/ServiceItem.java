package com.example.android.recyclerviewproject;

import java.util.ArrayList;


//class for one service within a program
public class ServiceItem {

    private String myDateStart;
    private String myDateEnd;
    private double myHrs;
    private String myDuties; //grab data from a large editText view findViewById(R.id.extra_duties)

    public ServiceItem(double hours, String startDate, String endDate, String duties){
        myHrs = hours;
        myDateStart = startDate;
        myDateEnd = endDate;
        myDuties = duties;
    }

    public double getHours(){
        return myHrs;
    }

    public String getStartDate(){ return myDateStart; }

    public String getEndDate(){return myDateEnd;}

    public String getMyDuties(){return myDuties;}
}
