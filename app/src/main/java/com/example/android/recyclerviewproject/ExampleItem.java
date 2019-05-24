package com.example.android.recyclerviewproject;

public class ExampleItem {
    //TODO #5 change int myhrs into double
    private int myHrs;
    private String myProg;
    private String myRole;

    public ExampleItem(String program, int hours, String role){
        myHrs = hours;
        myProg = program;
        myRole = role;
    }

    public String getProgram(){
        return myProg;
    }

    public int getHours(){
        return myHrs;
    }

    public String getRole(){
        return myRole;
    }
}
