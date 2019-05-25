package com.example.android.recyclerviewproject;

import java.util.ArrayList;

public class ExampleItem {
    private double myHrs;
    private ArrayList<ServiceItem> serviceList;
    private String myProg;
    private String myRole;

    public ExampleItem(String program, double hours, String role){
        myHrs = hours;
        myProg = program;
        myRole = role;
    }

    public String getProgram(){
        return myProg;
    }

    public double getHours(){
        return myHrs;
    }

    public String getRole(){
        return myRole;
    }
}