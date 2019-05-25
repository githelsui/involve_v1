package com.example.android.recyclerviewproject;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Iterator;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements AddServDialog.AddServeDialogListener {

    private RecyclerView myRecycler;
    private ExampleAdapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private ArrayList<ExampleItem> myList;
    private TextView myTotalHrs;
    private Button addServBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TEMPORARY FIELD (RESETS ALL SAVED ITEMS)
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref), MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();


        //perform these three main functions: load saved data, present on listview, initialize button instantaneously
        loadData();
        initRecyclerView();
        initAddButton();

        //TODO #8 create java class to store apps myTotal hours and update it using SharedPreferences
        myTotalHrs = findViewById(R.id.numhrs_lbl);
    }

    private void initRecyclerView() {
        //initialize all objects
        myRecycler = findViewById(R.id.recycler_view);
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ExampleAdapter(myList, this);

        //configure objects to the recyclerview
        myRecycler.setLayoutManager(myLayout);
        myRecycler.setAdapter(myAdapter);

        //configures button on each cardview by accessing this custom function from ExampleAdapter
        myAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openProgActivity(pos);
            }
        });
    }

    private void openProgActivity(int pos){
        //opens up ProgramActivity layout
        Intent myInt = new Intent(this, ProgramActivity.class);
        myInt.putExtra("Item", myList.get(pos));
        startActivity(myInt);
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

    private void openDialog() {
        AddServDialog myDialog = new AddServDialog();
        myDialog.show(getSupportFragmentManager(), "Add New Service Dialog");
    }

    //refreshes layout with new saved data
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref), MODE_PRIVATE);
        Gson myGson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.service_list), null);
        Type myType = new TypeToken<ArrayList <ExampleItem> >(){}.getType();
        myList = myGson.fromJson(json, myType);

        if(myList == null) myList = new ArrayList<>();
    }

    @Override //implements interface AddServeDialogListener; function is executed AFTER 'save' is clicked on dialog layout
    public void applyText(String name, double hrs, String myRole, RandomColor picker) {
        ExampleItem myItem = new ExampleItem(name, hrs, myRole, picker);
        myList.add(myItem); //adds new service into private arraylist
        initRecyclerView(); //refresh list on layout.xml
    }

    @Override //implements interface AddServeDialogListener; function is executed AFTER 'save' is clicked on dialog layout
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Gson myGson = new Gson();
        String json = myGson.toJson(myList);
        myEdit.putString(getString(R.string.service_list), json);
        myEdit.apply();
    }
}
