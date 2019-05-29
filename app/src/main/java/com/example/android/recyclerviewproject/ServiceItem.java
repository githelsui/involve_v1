package com.example.android.recyclerviewproject;

import android.os.Parcelable;
import java.util.ArrayList;
import android.os.Parcel;
import android.os.Parcelable;


//class for one service within a program
public class ServiceItem implements Parcelable{

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

    protected ServiceItem(Parcel in){

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

    public double getHours(){
        return myHrs;
    }

    public String getStartDate(){ return myDateStart; }

    public String getEndDate(){return myDateEnd;}

    public String getMyDuties(){return myDuties;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
