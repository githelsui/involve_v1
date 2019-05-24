package com.example.android.recyclerviewproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddServDialog.AddServeDialogListener {

    private RecyclerView myRecycler;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private ArrayList<ExampleItem> myList;
    private TextView myTotalHrs;
    private Button addServBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init private widgets and values
        myList = new ArrayList<ExampleItem>();
        myTotalHrs = findViewById(R.id.numhrs_lbl);
        addItemToList();
        initRecyclerView();
        initAddButton();
    }


    //RECYCLER VIEWS AND PRESENTING LIST OF PROGRAMS
    private void addItemToList() {
        ExampleItem temp = new ExampleItem("name", 12, "temp2");
        for (int i = 0; i < 10; i++) {
            myList.add(temp);
        }
    }

    private void initRecyclerView() {
        //initialize all objects
        myRecycler = findViewById(R.id.recycler_view);
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ExampleAdapter(myList);

        //configure objects to the recyclerview
        myRecycler.setLayoutManager(myLayout);
        myRecycler.setAdapter(myAdapter);
    }


    //ADD NEW PROGRAM BUTTON
    private void initAddButton() {
        addServBtn = findViewById(R.id.add_btn);
        addServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        AddServDialog myDialog = new AddServDialog();
        myDialog.show(getSupportFragmentManager(), "Add New Service Dialog");
    }

    @Override //implements interface AddServeDialogListener
    //TODO #4 change int hrs to double hrs
    public void applyText(String name, double hrs, String myRole) {
        ExampleItem myItem = new ExampleItem(name, hrs, myRole);
        myList.add(myItem);
        initRecyclerView(); //refresh list on layout.xml
    }
}
