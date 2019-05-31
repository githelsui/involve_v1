package com.example.android.recyclerviewproject.Custom_Object;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.example.android.recyclerviewproject.R;

public class RandomColor implements Parcelable {

    private int[] myArray;
    private View myView;
    private transient int totalColors;

    public RandomColor(){
        int[] arrColor = { R.drawable.red_grad, R.drawable.orange_grad, R.drawable.yellow_grad};
        myArray = new int[arrColor.length];
        copyArr(arrColor);

    }

    protected RandomColor(Parcel in){
        myArray = in.createIntArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(myArray);
    }

    public static final Creator<RandomColor> CREATOR = new Creator<RandomColor>() {
        @Override
        public RandomColor createFromParcel(Parcel source) {
            return new RandomColor(source);
        }

        @Override
        public RandomColor[] newArray(int size) {
            return new RandomColor[size];
        }
    };

    public int[] getMyArray(){
        return myArray;
    }

    private void copyArr(int[] temp){
        for(int i = 0; i < temp.length; i++){
            myArray[i] = temp[i];
        }
        totalColors = myArray.length;
    }

    public void setRandomColor(View layout){
        myView = layout;
        myView.setBackgroundResource(myArray[getRandomGradient()]);
    }

    public int getRandomGradient(){
        if(totalColors != 0){
            int num = (int)(Math.random() * (totalColors)); //includes index at 0
            return num;
        }
        return -1;
    }
}
