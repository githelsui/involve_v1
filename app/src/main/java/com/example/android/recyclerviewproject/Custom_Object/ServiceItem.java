package com.example.android.recyclerviewproject.Custom_Object;

import android.os.Parcelable;
import android.os.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;


//class for one service within a program
public class ServiceItem implements Parcelable{

    private String myDateStart, myDateEnd, startTime, endTime, myName, location, myDuties;
    private double myHrs;
    private int pos;
    private int sMonth, sDay, sYear, eMonth, eDay, eYear;

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


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(myHrs);
        dest.writeString(myDateStart);
        dest.writeString(myDateEnd);
        dest.writeString(myDuties);
        dest.writeString(myName);
    }

    public boolean isOneDay(){
        return myDateStart.equals(myDateEnd);
    }

    public boolean sameYear(){
       String yr1 = myDateStart.substring(myDateStart.length() - 4);
       String yr2 = myDateEnd.substring(myDateEnd.length() - 4);
       return yr1.equals(yr2);
    }

    public void setStartDatesInt(int month, int day, int yr) {
        sMonth = month;
        sDay = day;
        sYear = yr;
    }

    public void setEndDatesInt(int month, int day, int yr) {
        eMonth = month;
        eDay = day;
        eYear = yr;
    }

    public int geteMonth() {
        return eMonth;
    }

    public int geteDay() {
        return eDay;
    }

    public int geteYear() {
        return eYear;
    }

    public int getsMonth() {
        return sMonth;
    }

    public int getsDay() {
        return sDay;
    }

    public int getsYear() {
        return sYear;
    }

    public  String  getMyName(){return myName;}

    public boolean sameTime(){
        return startTime.equals(endTime);
    }

    public void setName(String temp){
        myName = temp;
    }

    public void setPos(int i){pos = i;}
    public int getPos(){return pos;}

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

    public void setStartTime(String temp){ startTime = temp;}

    public void setEndTime(String temp){ endTime = temp; }

    public String getStartTime(){ return startTime; }

    public String getEndTime(){ return endTime; }

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
