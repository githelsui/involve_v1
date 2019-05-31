package com.example.android.recyclerviewproject;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ExampleItem implements Parcelable{
    private double myHrs;
    private ArrayList<ServiceItem> serviceList;
    private String myProg;
    private String myRole;
    private transient RandomColor myPick;
    private int myColor;
    private ServiceAdapter myAdapter;
    private int position;


    public ExampleItem(String program, double hours, String role, RandomColor picker){
        //intrinsic values
        myHrs = hours;
        myProg = program;
        myRole = role;
        serviceList = new ArrayList<>();

        //colors
        myPick = picker;
        myColor = myPick.getRandomGradient();

        //list view
        myAdapter = new ServiceAdapter(serviceList);
    }

    public int getPosition(){
        return position;
    }

    public void setPos(int temp){
        position = temp;
    }

    public RandomColor getMyPick(){
        return myPick;
    }

    public void setMyAdapter(ArrayList<ServiceItem> list){
        myAdapter = new ServiceAdapter(list);
    }

    public ServiceAdapter getMyAdapter(){
        return myAdapter;
    }

    public void setList(ArrayList<ServiceItem> temp){
        serviceList = temp;
    }

    protected ExampleItem(Parcel in) {
        myHrs = in.readDouble();
        myProg = in.readString();
        myRole = in.readString();
        myColor = in.readInt();
        serviceList = in.readArrayList(ServiceItem.class.getClassLoader());
        position = in.readInt();
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
        dest.writeInt(myColor);
        dest.writeList(serviceList);
        dest.writeInt(position);
    }

    public void addItem(ServiceItem temp){
        if(serviceList != null){
            serviceList.add(temp);
        }else {
            serviceList = new ArrayList<>();
            serviceList.add(temp);
        }
    }

    public ArrayList<ServiceItem> getServiceList(){
        return serviceList;
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

    public int getMyColor(){
        return myColor;}

}