package com.example.android.recyclerviewproject;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Type;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
    private static final int REQUEST_CODE = 5;
    private ArrayList<ExampleItem> myList;
    private TextView myTotalHrs;
    private Button addServBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED PREF", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        loadData();
        initRecyclerView();
        initAddButton();

        //TODO #8 create java class to store apps myTotal hours and update it using SharedPreferences
        myTotalHrs = findViewById(R.id.numhrs_lbl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
           ExampleItem passedItem =(ExampleItem)(data.getExtras().get("passed_item"));
           myList.get(passedItem.getPosition()).setList(passedItem.getServiceList());
        }
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
        ExampleItem temp = myList.get(pos);
        temp.setPos(pos);
        myInt.putExtra("Item", temp);
        startActivityForResult(myInt, REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED PREF", MODE_PRIVATE);
        Gson myGson = new Gson();
        String json = sharedPreferences.getString("service_list", null);
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
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Gson myGson = new Gson();
         String json = myGson.toJson(myList);
        myEdit.putString("service_list", json);
        myEdit.apply();
    }
}
