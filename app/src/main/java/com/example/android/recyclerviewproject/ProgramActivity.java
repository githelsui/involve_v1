package com.example.android.recyclerviewproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        //TESharedPreferences sharedPreferences = getSharedPreferences("service_prefs", MODE_PRIVATE);
        ////        sharedPreferences.edit().clear().commit();MPORARY FIELD (RESETS ALL SAVED ITEMS)
//

        loadData();
        getParceables();
        setColorChoices();
        setLayouts();
        initRecyclerView();
        initAddButton();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("service_prefs", MODE_PRIVATE);
        Gson myGson = new Gson();
        String json = sharedPreferences.getString("dates_list", null);
        Type myType = new TypeToken<ArrayList <ServiceItem> >(){}.getType();
        serviceList = myGson.fromJson(json, myType);

        if(serviceList == null) serviceList = new ArrayList<>();
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
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initRecyclerView() {
        //initialize all objects
        myRecycler = findViewById(R.id.serverecycler_view);
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ServiceAdapter(serviceList);

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

    @Override
    public void applyServiceText(double hours, String startDate, String endDate, String duties) {
        ServiceItem temp = new ServiceItem(hours, startDate, endDate, duties);
        myItem.addItem(temp);
        serviceList = myItem.getServiceList();
        //copyList(myItem.getServiceList());
        initRecyclerView();
    }

    private void copyList(ArrayList<ServiceItem> list){
        ArrayList<ServiceItem> temp = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            ServiceItem tempIt = list.get(i);
            temp.add(tempIt);
        }
        serviceList = temp;
    }

    @Override
    public void saveServiceData() {
        SharedPreferences sharedPreferences = getSharedPreferences("service_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Gson myGson = new Gson();
        String json = myGson.toJson(serviceList);
        System.out.println("SIZE: " + myItem.getServiceList().size());
        myEdit.putString("dates_list", json);
        myEdit.apply();
    }
}
