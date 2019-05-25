package com.example.android.recyclerviewproject;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;

public class RandomColor {

    private int[] myArray;
    private View myView;
    private int totalColors;

    public RandomColor(View layout){
        myView = layout;
        int[] arrColor = { R.drawable.red_grad, R.drawable.orange_grad, R.drawable.yellow_grad};
        myArray = new int[arrColor.length];
        copyArr(arrColor);

    }

    private void copyArr(int[] temp){
        for(int i = 0; i < temp.length; i++){
            myArray[i] = temp[i];
        }
        totalColors = myArray.length;
        System.out.println("TOTAL ITEMS = " + totalColors);
    }

    public void setRandomColor(){
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
