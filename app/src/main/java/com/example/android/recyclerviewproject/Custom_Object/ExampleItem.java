package com.example.android.recyclerviewproject.Custom_Object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Collections;
import android.os.Parcel;
import android.os.Parcelable;

public class ExampleItem implements Parcelable{
    private double myHrs;
    private ArrayList serviceList;
    private String myProg;
    private String myAdvisor;
    private String myRole;
    private transient RandomColor myPick;
    private int myColor;
    private double initialHr;
    private int position;


    public ExampleItem(String program, double hours, String role, RandomColor picker){
        //intrinsic values
        myHrs = hours;
        initialHr = hours;
        myProg = program;
        myRole = role;
        serviceList = new ArrayList<>();

        //colors
        myPick = picker;
        myColor = myPick.getRandomGradient();
    }

    protected ExampleItem(Parcel in) {
        myHrs = in.readDouble();
        myProg = in.readString();
        myRole = in.readString();
        myColor = in.readInt();
        serviceList = in.readArrayList(ServiceItem.class.getClassLoader());
        position = in.readInt();
        initialHr = in.readDouble();
        myAdvisor = in.readString();
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
        dest.writeDouble(initialHr);
        dest.writeString(myAdvisor);
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

    public int getMyColor(){ return myColor;}


    //old initial is 8
    //new initial is 6

    //if there are serviceitems present-> totalhours = serviceitems.hours + new initial-oldinitial
    //total hours should be 8
    public void setInitialHr(double temp) {
        double oldInitial = initialHr;
        initialHr = temp;
        if(initialHr == myHrs) myHrs = temp;
        else{
            if(oldInitial<initialHr) myHrs += initialHr-oldInitial;
            else myHrs -= oldInitial-initialHr;
        }
    }
    public void setMyRole(String temp){ myRole = temp;}
    public void setAdvisor(String temp){myAdvisor = temp;}
    public void setProgramName(String temp){myProg = temp;}


    public void removeItem(int i){
        serviceList.remove(i);
    }

    public void setMyAdvisor(String name){
        myAdvisor = name;
    }

    public String getAdvisor(){
        return myAdvisor;
    }

    public void addHrs(double i){
        myHrs += i;
    }

    public void subtractHrs(double i) { myHrs -= i; }

    public int getPosition(){
        return position;
    }

    public void setPos(int temp){
        position = temp;
    }

    public RandomColor getMyPick(){
        return myPick;
    }

    public void setList(ArrayList<ServiceItem> temp){
        serviceList = temp;
    }

    public void setHrs(double i){
        myHrs = i;
    }

    public void sortDates() throws Exception {
        ArrayList<Date> allDates = new ArrayList<>();
        for(int i = 0; i < serviceList.size(); i++){
            ServiceItem temp = (ServiceItem)serviceList.get(i);
            allDates.add(temp.parseToDate());
        }
    }

    public double getInitialHr(){
        return initialHr;
    }
}