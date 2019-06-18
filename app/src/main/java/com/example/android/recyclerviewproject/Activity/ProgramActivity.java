package com.example.android.recyclerviewproject.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.Dialog.DateDialog;
import com.example.android.recyclerviewproject.Dialog.DeleteProgramDialog;
import com.example.android.recyclerviewproject.Dialog.EditEventDialog;
import com.example.android.recyclerviewproject.Dialog.EditProgram;
import com.example.android.recyclerviewproject.Dialog.EventDialog;
import com.example.android.recyclerviewproject.Helper.RecyclerItemTouchHelper;
import com.example.android.recyclerviewproject.Helper.RecyclerServiceTouchHelper;
import com.example.android.recyclerviewproject.R;
import com.example.android.recyclerviewproject.Dialog.ServeInfoDialog;
import com.example.android.recyclerviewproject.Adapter.ServiceAdapter;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;

import java.util.ArrayList;

public class ProgramActivity extends AppCompatActivity implements ServeInfoDialog.ServeInfoDialogListener,
        DeleteProgramDialog.DeleteProgramDialogListener, EventDialog.EventDialogListener, EditProgram.EditProgramListener, DateDialog.DateDialogListener, EditEventDialog.EditEventListener {

    private ArrayList<ServiceItem> serviceList;
    private double deleteHrs;
    private TextView myName;
    private CardView hiddenView;
    private double tempHrs;
    private TextView myHrs;
    private Button addServBtn;
    private RecyclerView myRecycler;
    private ServiceAdapter myAdapter;
    private CardView introMsg;
    private ExampleItem myItem;
    private ImageView btnDown;
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

        btnDown = findViewById(R.id.reveal_button);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openInfo();
            }
        });
    }

    private void openInfo(){
        RelativeLayout setColorInfo = findViewById(R.id.hidden_relative);
        hiddenView = findViewById(R.id.program_info);
        setColorInfo.setBackgroundResource(colorChoices[myItem.getMyColor()]);
        hiddenView.setVisibility(View.VISIBLE);
        btnDown.setImageResource(R.drawable.ic_edit);
        btnDown.setRotation(360);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                editInfo();
            }
        });
        ImageView closeUp = findViewById(R.id.backup_button);
        closeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeInfo();
            }
        });
        setInfo();
    }

    @SuppressLint("SetTextI18n")
    private void setInfo(){
        TextView initialhrs = findViewById(R.id.initialhours);
        TextView role = findViewById(R.id.role);
        TextView advisor = findViewById(R.id.advisor);
        initialhrs.setText("Initial hours added to " + myItem.getProgram()
                + ": " + Double.toString(myItem.getInitialHr()));
        if(myItem.getRole().equals("")) role.setText("Role: " + getString(R.string.n_a));
        else role.setText("Role: " + myItem.getRole());
        if(myItem.getAdvisor() == null) advisor.setText("Advisor: " + getString(R.string.n_a));
        else advisor.setText("Advisor: " + myItem.getAdvisor());
    }

    private void closeInfo(){
        hiddenView.setVisibility(View.INVISIBLE);
        btnDown.setImageResource(R.drawable.ic_arrow);
        btnDown.setRotation(90);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openInfo();
            }
        });
    }

    private void editInfo(){
        EditProgram myDialog = new EditProgram();
        myDialog.setMyItem(myItem);
        myDialog.show(getSupportFragmentManager(), "Edit Program");
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
        System.out.println("Size: " + myItem.getServiceList().size());
        System.out.println("Hors: " + myItem.getHours());
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
        myDialog.setMyContext(ProgramActivity.this);
        myDialog.show(getSupportFragmentManager(), "Add New Service Dialog");
    }

    @Override //for animation
    public void finish() {
        Intent returnIntent = new Intent();
       // myItem.setHrs(myHrs);
        serviceList = myItem.getServiceList();
        //returnIntent.putExtra("passed_list", serviceList);
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
        introMsg = findViewById(R.id.introview);
        if(myItem.getServiceList().size() == 0){
            introMsg.setVisibility(View.VISIBLE);
            myRecycler.setVisibility(View.INVISIBLE);
        }
        else{
            introMsg.setVisibility(View.INVISIBLE);
            myRecycler.setVisibility(View.VISIBLE);
        }
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
                openEventDialog(pos);
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

    private void openEventDialog(int pos){
        EventDialog dialog = new EventDialog();
        dialog.setColorCode(myItem.getMyColor());
        dialog.setService(myItem.getServiceList().get(pos));
        dialog.setMyContext(ProgramActivity.this);
        dialog.show(getSupportFragmentManager(), "View Event Info");
    }

    private void openDeleteDialog(RecyclerView.ViewHolder view){
        DeleteProgramDialog myDialog = new DeleteProgramDialog();
        myDialog.setView(view);
        myDialog.show(getSupportFragmentManager(), "Delete Program");
    }

    private void updateHrs(double i){
        tempHrs += i;
    }
    private void subtHrs(double i){deleteHrs += i;}

    @Override
    public void applyServiceText(double hours, String startDate, String endDate, String duties, String name) {
        ServiceItem temp = new ServiceItem(hours, startDate, endDate, duties, name);
        myItem.addItem(temp);
        myItem.addHrs(hours);
        updateHrs(hours);
        initRecyclerView();
        Toast msg = Toast.makeText(getApplicationContext(), "New Service Added", Toast.LENGTH_SHORT);
        msg.show();
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

    @Override
    public void editEvent() {

    }

    @Override
    public void editProgram(ExampleItem passed) {
        myItem = passed;
        updateHrs(myItem.getHours());
        setLayouts();
        closeInfo();
        initRecyclerView();
    }


    @Override
    public void saveData(ServiceItem item) {
            //TODO save service item after being edited
    }

    @Override
    public void passServeElements() {
        //create
    }
}
