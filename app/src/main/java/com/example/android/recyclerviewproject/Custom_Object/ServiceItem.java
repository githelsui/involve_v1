package com.example.android.recyclerviewproject.Custom_Object;

import android.os.Parcelable;
import android.os.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;


//class for one service within a program
public class ServiceItem implements Parcelable{

    private String myDateStart;
    private String myDateEnd;
    private String myName;
    private String location;
    private double myHrs;
    private String myDuties;

    public ServiceItem(double hours, String startDate, String endDate, String duties, String name){
        myHrs = hours;
        myDateStart = startDate;
        myDateEnd = endDate;
        myDuties = duties;
        myName = name;
    }

    protected ServiceItem(Parcel in){
        myHrs = in.readDouble();
        myDateStart = in.readString();
        myDateEnd = in.readString();
        myDuties =in.readString();
        myName = in.readString();
    }

    public static final Creator<ServiceItem> CREATOR = new Creator<ServiceItem>(){
        @Override
        public ServiceItem createFromParcel(Parcel source) {
            return new ServiceItem(source);
        }

        @Override
        public ServiceItem[] newArray(int size) {
            return new ServiceItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    //TODO http://prasanta-paul.blogspot.com/2010/06/android-parcelable-example.html
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(myHrs);
        dest.writeString(myDateStart);
        dest.writeString(myDateEnd);
        dest.writeString(myDuties);
        dest.writeString(myName);
    }

    public  String  getMyName(){return myName;}

    public void setName(String temp){
        myName = temp;
    }

    public void setHours(double i){
        myHrs = i;
    }

    public void setDate(String temp){
        myDateStart = temp;
    }

    public void setDuty(String temp){
        myDuties = temp;
    }

    public void setLoc(String temp){
        location = temp;
    }

    public double getHours(){
        return myHrs;
    }

    public String getStartDate(){ return myDateStart; }

    public String getEndDate(){return myDateEnd;}

    public String getMyDuties(){return myDuties;}

    public String getLocation() {return location;}

    public Date parseToDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        //throws exception when String mydatestart does not follow format properly
        try{
           return format.parse(myDateStart);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
