package com.example.android.recyclerviewproject;

public class ExampleItem {
    private double myHrs;
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
