package com.example.android.recyclerviewproject.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import java.lang.reflect.Type;

import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;
import com.example.android.recyclerviewproject.Dialog.AddServDialog;
import com.example.android.recyclerviewproject.Adapter.ExampleAdapter;
import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.Dialog.DeleteProgramDialog;
import com.example.android.recyclerviewproject.Helper.RecyclerItemTouchHelper;
import com.example.android.recyclerviewproject.R;
import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddServDialog.AddServeDialogListener, DeleteProgramDialog.DeleteProgramDialogListener {

    private RecyclerView myRecycler;
    private ExampleAdapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private ItemTouchHelper.SimpleCallback touchHelper;
    private double totalHours;
    private static final int REQUEST_CODE = 5;
    private ArrayList<ExampleItem> myList;
    private TextView myTotalHrs;
    private Button addServBtn;
    private CardView introMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED PREF", MODE_PRIVATE);
        SharedPreferences myHrs = getSharedPreferences("my_hours", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        myHrs.edit().clear().commit();

        loadData();
        initRecyclerView();
        initAddButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
           ExampleItem passedItem =(ExampleItem)(data.getExtras().get("passed_item"));
           double passedHour = (double) (data.getExtras().get("passed_hour"));
           double deleteHrs = (double) (data.getExtras().get("passed_delete"));
           int position = (passedItem.getPosition());
           myList.remove(position);
           myList.add(position, passedItem);
           addTotalHours(passedHour);
           subtractHours(deleteHrs);

           initRecyclerView();
        }
    }

    private void initRecyclerView() {
        myRecycler = findViewById(R.id.recycler_view);
        if(myList.size() == 0){
            introMsg = findViewById(R.id.introview);
            introMsg.setVisibility(View.VISIBLE);
            myRecycler.setVisibility(View.INVISIBLE);
        }
        myTotalHrs = findViewById(R.id.numhrs_lbl);
        myTotalHrs.setText(totalHours + " hours");
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ExampleAdapter(myList, this);
        myRecycler.setLayoutManager(myLayout);
        myRecycler.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openProgActivity(pos);
            }
        });
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                        openDeleteDialog(viewHolder);
                    }
                });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(myRecycler);

    }

    public void openDeleteDialog(RecyclerView.ViewHolder view){
        DeleteProgramDialog myDialog = new DeleteProgramDialog();
        myDialog.setView(view);
        myDialog.show(getSupportFragmentManager(), "Delete Program");
    }

    private void updateTotalHours(double i){
        totalHours = i;
    }

    private void addTotalHours(double i){
        totalHours += i;
    }

    private void subtractHours(double i) { totalHours -= i; }

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

    @Override
    public void applyText(String name, double hrs, String myRole, RandomColor picker) {
        ExampleItem myItem = new ExampleItem(name, hrs, myRole, picker);
        myList.add(myItem);
        addTotalHours(hrs);
        introMsg.setVisibility(View.INVISIBLE);
        myRecycler.setVisibility(View.VISIBLE);
        Toast msg = Toast.makeText(getApplicationContext(), "New Program Added", Toast.LENGTH_SHORT);
        msg.show();
        initRecyclerView();
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


    @Override
    public void removeProgram(RecyclerView.ViewHolder temp) {
        int position = temp.getAdapterPosition();
        subtractHours(myList.get(position).getHours());
        myList.remove(position);
        initRecyclerView();
        myAdapter.notifyItemRemoved(position);
        Toast msg = Toast.makeText(getApplicationContext(), "Program Removed from List", Toast.LENGTH_SHORT);
        msg.show();
        saveData();
    }

    @Override
    public void notifyChanges(RecyclerView.ViewHolder temp) {
        myAdapter.notifyItemChanged(temp.getAdapterPosition());
    }
}
