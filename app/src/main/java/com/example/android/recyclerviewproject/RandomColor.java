package com.example.android.recyclerviewproject;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;

public class RandomColor {

    private TypedArray allColors;
    private Resources myRes;
    private View myView;
    private int totalColors;

    public RandomColor(View view){
        myView = view;
        myRes = view.getResources();
        allColors = myRes.obtainTypedArray(R.array.random_gradients); //TYPED ARRAY OF ALL GRADIENTS INSIDE RES/DRAWABLE
        totalColors = allColors.getIndexCount();
    }

    public Drawable getRandomGradient(){
        int num = (int)(Math.random() * totalColors); //includes index at 0
        return myRes.getDrawable(num, myRes.newTheme());
    }
}
