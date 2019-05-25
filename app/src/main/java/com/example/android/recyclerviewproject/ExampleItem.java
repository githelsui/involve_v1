package com.example.android.recyclerviewproject;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ExampleItem implements Parcelable{
    private double myHrs;
    private ArrayList<ServiceItem> serviceList;
    private String myProg;
    private String myRole;

    public ExampleItem(String program, double hours, String role){
        myHrs = hours;
        myProg = program;
        myRole = role;
    }

    protected ExampleItem(Parcel in) {
        myHrs = in.readDouble();
        myProg = in.readString();
        myRole = in.readString();
    }

    public static final Creator<ExampleItem> CREATOR = new Creator<ExampleItem>() {
        @Override
        public ExampleItem createFromParcel(Parcel in) {
            return new ExampleItem(in);
        }

        @Override
        public ExampleItem[] newArray(int size) {
            return new ExampleItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(myHrs);
        dest.writeString(myProg);
        dest.writeString(myRole);
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