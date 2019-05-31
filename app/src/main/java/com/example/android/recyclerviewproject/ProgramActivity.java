package com.example.android.recyclerviewproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProgramActivity extends AppCompatActivity implements ServeInfoDialog.ServeInfoDialogListener {

    private ArrayList<ServiceItem> serviceList;
    private TextView myName;
    private Button addServBtn;
    private RecyclerView myRecycler;
    private ServiceAdapter myAdapter;
    private ExampleItem myItem;
    private RelativeLayout cardLayout;
    private int[] colorChoices;
    private RecyclerView.LayoutManager myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
//       sharedPreferences.edit().clear().commit();

        //loadData();
        getParceables();
        setColorChoices();
        setLayouts();
        initRecyclerView();
        initAddButton();
    }

    private void loadData(){
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyItem", "");
        myItem = gson.fromJson(json, ExampleItem.class);

        if (myItem != null) serviceList = myItem.getServiceList();
        else getParceables();
    }

    private void setLayouts(){
        myName = findViewById(R.id.serveprog_name);
        myName.setText(myItem.getProgram());

        cardLayout = findViewById(R.id.program_layout);
        cardLayout.setBackgroundResource(colorChoices[myItem.getMyColor()]);
    }

    private void setColorChoices(){
        int[] arr = {R.drawable.red_grad, R.drawable.orange_grad, R.drawable.yellow_grad};
        colorChoices = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            colorChoices[i] = arr[i];
        }
    }

    private void getParceables(){ //gains access to item's intrinsic values (name, hours, role, etc)
        Intent myInt = getIntent();
        myItem = myInt.getParcelableExtra("Item");
        serviceList = myItem.getServiceList();
    }

    private void initAddButton() {
        addServBtn = findViewById(R.id.addserve_btn);
        addServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        ServeInfoDialog myDialog = new ServeInfoDialog();
        myDialog.show(getSupportFragmentManager(), "Add New Service Dialog");
    }

    @Override //for animation
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("passed_item", myItem); //myItem with an UPDATED service list
        setResult(RESULT_OK, returnIntent); //By not passing the intent in the result, the calling activity will get null data.
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initRecyclerView() {
        //initialize all objects
        myRecycler = findViewById(R.id.serverecycler_view);
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ServiceAdapter(myItem.getServiceList());

        //configure objects to the recyclerview
        myRecycler.setLayoutManager(myLayout);
        myRecycler.setAdapter(myAdapter);

        //configures button on each cardview by accessing this custom function from ServiceAdapter
        myAdapter.setOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //TODO #7 create a dialog for a service item when arrow button is clicked(fill with info)
            }
        });
    }

//    private void checkAdapter(){
//        if(myItem.getMyAdapter() != null) System.out.println("NOT NULL!");
//        else{
//            System.out.println("NULL ADAPTER");
//            myItem.setMyAdapter(serviceList);
//            if(myItem.getMyAdapter() != null){
//                System.out.println("CREATED NEW ADAPTER WITH SIZE: " + myItem.getMyAdapter().getItemCount());
//            }
//            else System.out.println("Adapter is still null even after creation");
//        }
//
//        Intent intent = getIntent();
//        ServiceAdapter temp = myItem.getMyAdapter();
//        finish();
//        intent.putExtra("MyAdapter", temp);
//        startActivity(intent);
//
//    }

    @Override
    public void applyServiceText(double hours, String startDate, String endDate, String duties) {
        ServiceItem temp = new ServiceItem(hours, startDate, endDate, duties);
        myItem.addItem(temp);
        initRecyclerView();
    }


    @Override
    public void saveServiceData() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myItem);
        prefsEditor.putString("MyItem", json);
        prefsEditor.commit();
    }
}
