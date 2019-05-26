package com.example.android.recyclerviewproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProgramActivity extends AppCompatActivity {

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

        serviceList = new ArrayList<>();

        //example of service
        serviceList.add(new ServiceItem(0.0, "some", "thing"));

        getParceables();
        setColorChoices();
        setLayouts();
        initRecyclerView();
        initAddButton();
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

    @Override
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

}
