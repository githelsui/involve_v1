package com.example.android.recyclerviewproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProgramActivity extends AppCompatActivity {

    //TODO #4 Define private widgets (recyclerviews + helpers) and the private arraylist to be presented
    private ArrayList<ServiceItem> serviceList;
    private TextView myName;
    private RecyclerView myRecycler;
    private ExampleAdapter myAdapter;
    private ExampleItem myItem;
    private RecyclerView.LayoutManager myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        getParceables();

        myName = findViewById(R.id.serveprog_name);
        myName.setText(myItem.getProgram());
    }

    private void getParceables(){ //gains access to item's values (name, hours, role, etc)
        Intent myInt = getIntent();
        myItem = myInt.getParcelableExtra("Item");
    }

    //TODO #2 Create a recycler view that presents a program's individual services & hours
    //TODO #3 Create another Adapter class that connects a recyclerview to programactivity
                //must be a different adapter class than the one used for mainactivity
}
