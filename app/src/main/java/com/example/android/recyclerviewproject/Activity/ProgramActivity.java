package com.example.android.recyclerviewproject.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.Dialog.DeleteProgramDialog;
import com.example.android.recyclerviewproject.Helper.RecyclerItemTouchHelper;
import com.example.android.recyclerviewproject.Helper.RecyclerServiceTouchHelper;
import com.example.android.recyclerviewproject.R;
import com.example.android.recyclerviewproject.Dialog.ServeInfoDialog;
import com.example.android.recyclerviewproject.Adapter.ServiceAdapter;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;

import java.util.ArrayList;

public class ProgramActivity extends AppCompatActivity implements ServeInfoDialog.ServeInfoDialogListener, DeleteProgramDialog.DeleteProgramDialogListener {

    private ArrayList<ServiceItem> serviceList;
    private double deleteHrs;
    private TextView myName;
    private double tempHrs;
    private TextView myHrs;
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

        myHrs = findViewById(R.id.servehrs_lbl);
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
       // myItem.setHrs(myHrs);
        returnIntent.putExtra("passed_item", myItem);
        returnIntent.putExtra("passed_delete", deleteHrs);
        returnIntent.putExtra("passed_hour", tempHrs);
        setResult(RESULT_OK, returnIntent);
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initRecyclerView() {
        myHrs.setText(Double.toString(myItem.getHours()) + " hours");
        myRecycler = findViewById(R.id.serverecycler_view);
        myRecycler.setHasFixedSize(true);
        myLayout = new LinearLayoutManager(this);
        myAdapter = new ServiceAdapter(myItem.getServiceList(), myItem);

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

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerServiceTouchHelper(0, ItemTouchHelper.LEFT, new RecyclerServiceTouchHelper.RecyclerItemTouchHelperListener() {
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

    private void updateHrs(double i){
        tempHrs += i;
    }
    private void subtHrs(double i){deleteHrs += i;}

    @Override
    public void applyServiceText(double hours, String startDate, String endDate, String duties) {
        ServiceItem temp = new ServiceItem(hours, startDate, endDate, duties);
        myItem.addItem(temp);
        myItem.addHrs(hours);
        updateHrs(hours);
        Toast msg = Toast.makeText(getApplicationContext(), "New Service Added", Toast.LENGTH_SHORT);
        msg.show();
        initRecyclerView();
    }

    @Override
    public void removeProgram(RecyclerView.ViewHolder temp) {
        int position = temp.getAdapterPosition();
        ArrayList<ServiceItem> tempList = myItem.getServiceList();
        myItem.subtractHrs(tempList.get(position).getHours());
        subtHrs(tempList.get(position).getHours());
        initRecyclerView();
        myItem.removeItem(position);
        myAdapter.notifyItemRemoved(position);
        Toast msg = Toast.makeText(getApplicationContext(), "Service Removed from List", Toast.LENGTH_SHORT);
        msg.show();
    }

    @Override
    public void notifyChanges(RecyclerView.ViewHolder temp) {
        myAdapter.notifyItemChanged(temp.getAdapterPosition());
    }
}
