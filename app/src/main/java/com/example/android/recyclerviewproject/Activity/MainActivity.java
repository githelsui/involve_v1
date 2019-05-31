package com.example.android.recyclerviewproject.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.recyclerviewproject.Dialog.AddServDialog;
import com.example.android.recyclerviewproject.Adapter.ExampleAdapter;
import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.Helper.RecyclerItemTouchHelper;
import com.example.android.recyclerviewproject.R;
import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddServDialog.AddServeDialogListener {

    private RecyclerView myRecycler;
    private ExampleAdapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private ItemTouchHelper.SimpleCallback touchHelper;
    private double totalHours;
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
           ExampleItem passedItem =(ExampleItem)(data.getExtras().get("passed_item"));
           myList.get(passedItem.getPosition()).setList(passedItem.getServiceList());
           myList.get(passedItem.getPosition()).setHrs(passedItem.getHours());
           updateTotalHours(myList.get(passedItem.getPosition()).getHours());
           initRecyclerView();
        }
    }

    private void initRecyclerView() {
        myTotalHrs = findViewById(R.id.numhrs_lbl);
        myTotalHrs.setText(totalHours + " hours");

        myRecycler = findViewById(R.id.recycler_view);
        myRecycler.setHasFixedSize(true);
        myRecycler.setLayoutManager(myLayout);
        myRecycler.setAdapter(myAdapter);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ExampleAdapter(myList, this);
        myRecycler.setItemAnimator(new DefaultItemAnimator());
        myRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //touch item -> open ProgramActivity
        myAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openProgActivity(pos);
            }
        });

        //swipe item -> reveal background view of exampleitem
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//                        if (direction == ItemTouchHelper.LEFT) {
//                            myAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
//                        }
                    }

                });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(myRecycler);

    }

    private void updateTotalHours(double i){
        totalHours = i;
    }

    private void addTotalHours(double i){
        totalHours += i;
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

        double temp = Double.longBitsToDouble(sharedPreferences.getLong("my_hours", Double.doubleToLongBits(0)));
        totalHours = temp;

        }

    @Override //implements interface AddServeDialogListener; function is executed AFTER 'save' is clicked on dialog layout
    public void applyText(String name, double hrs, String myRole, RandomColor picker) {
        ExampleItem myItem = new ExampleItem(name, hrs, myRole, picker);
        myList.add(myItem); //adds new service into private arraylist
        addTotalHours(hrs);
        updateTotalHours(hrs);
        initRecyclerView(); //refresh list on layout.xml
    }

    @Override //implements interface AddServeDialogListener; function is executed AFTER 'save' is clicked on dialog layout
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Gson myGson = new Gson();
        String json = myGson.toJson(myList);
        myEdit.putString("service_list", json);
        myEdit.putLong("my_hours", Double.doubleToRawLongBits(totalHours));
        myEdit.apply();

    }
}
