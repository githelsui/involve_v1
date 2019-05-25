package com.example.android.recyclerviewproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ProgramActivity extends AppCompatActivity {

    //TODO #4 Define private widgets (recyclerviews + helpers) and the private arraylist to be presented
    private ArrayList<ServiceItem> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
    }

    //TODO #2 Create a recycler view that presents a program's individual services & hours
    //TODO #3 Create another Adapter class that connects a recyclerview to programactivity
                //must be a different adapter class than the one used for mainactivity
}
