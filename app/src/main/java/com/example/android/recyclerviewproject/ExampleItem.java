package com.example.android.recyclerviewproject;

public class ExampleItem {
    private int imageValue;
    private String text1;
    private String text2;

    public ExampleItem(int img, String txt1, String txt2){
        imageValue = img;
        text1 = txt1;
        text2 = txt2;
    }

    public int getImageValue(){
        return imageValue;
    }

    public String getText1(){
        return text1;
    }

    public String getText2(){
        return text2;
    }
}
