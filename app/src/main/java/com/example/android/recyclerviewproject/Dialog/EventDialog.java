package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;
import com.example.android.recyclerviewproject.R;

import static android.content.ContentValues.TAG;

public class EventDialog extends AppCompatDialogFragment{

    private RelativeLayout service_layout;
    private TextView eventName;
    private int[] colorChoices;
    private EditText name;
    private EditText dates;
    private EditText duty;
    private EditText eventHour;
    private EditText times;
    private EditText location;
    private ServiceItem myEvent;
    private int colorCode;
    private Context myContext;
    private View mainView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.event_dialog, null);
        builder.setView(myView);
        initViews(myView);
        mainView = myView;
        final Dialog myDialog = builder.create();
        myDialog.setCanceledOnTouchOutside(false);
        Window myWindow = myDialog.getWindow();
        myWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myWindow.getAttributes().windowAnimations = R.style.EventDialogTheme;
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ImageView closeBtn = myView.findViewById(R.id.closebtn);
                Button editBtn = myView.findViewById(R.id.edit_eventbtn);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                       showNameDialog();
                    }
                });
                dates.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                        showDatesDialog();
                    }
                });
                times.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                        showTimeDialog();
                    }
                });
                eventHour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                        showHours();
                    }
                });
                duty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                        showDuty();
                    }
                });
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                        showLoc();
                    }
                });
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                     showNameDialog();
                    }
                });
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
            }
        });
        return myDialog;
    }

    public void setMyContext(Context cont){
        myContext = cont;
    }

    private void showNameDialog(){
        NameDialog nameDialog = new NameDialog();
        try {
            FragmentManager fragmentManager = ((FragmentActivity) myContext).getSupportFragmentManager();
        } catch (ClassCastException e) {
            Log.e(TAG, "Can't get fragment manager");
        }
        nameDialog.setItem(myEvent);
        nameDialog.setColor(colorCode);
        nameDialog.setCont(myContext);
        nameDialog.setCancelable(false);
        nameDialog.show(getFragmentManager(), "Edit Name");
    }

    private void showDatesDialog(){
        DatesDialog datesDialog = new DatesDialog();
        try {
            FragmentManager fragmentManager = ((FragmentActivity) myContext).getSupportFragmentManager();
        } catch (ClassCastException e) {
            Log.e(TAG, "Can't get fragment manager");
        }
        datesDialog.setItem(myEvent);
        datesDialog.setColor(colorCode);
        datesDialog.setCont(myContext);
        datesDialog.setCancelable(false);
        datesDialog.show(getFragmentManager(), "Edit Dates");
    }

    private void showTimeDialog(){

    }

    private void showHours(){

    }

    private void showDuty(){

    }

    private void showLoc(){

    }

    public void setColorCode(int i){
        colorCode = i;
        colorChoices = new RandomColor().getMyArray();
    }

    public void setService(ServiceItem temp){
        myEvent = temp;
    }

    private void initViews(View myView){
        service_layout = myView.findViewById(R.id.event_color);
        service_layout.setBackgroundResource(colorChoices[colorCode]);

        name = myView.findViewById(R.id.eventname);
        if(myEvent.getMyName() != null) name.setText(myEvent.getMyName());
        else name.setText(getString(R.string.no_name));

        eventName = myView.findViewById(R.id.event_name);
        if(myEvent.getMyName() != null){
            eventName.setText(myEvent.getMyName());
        }
        else{
            eventName.setText(getString(R.string.no_name));
            myEvent.setName("Untitled Event");
        }

        dates = myView.findViewById(R.id.event_date);
        if(myEvent.isOneDay()) dates.setText(myEvent.getStartDate());
        else dates.setText(myEvent.getStartDate() + " - " + myEvent.getEndDate());

        times = myView.findViewById(R.id.event_times);
        if(myEvent.sameTime())times.setText(myEvent.getStartTime());
        else times.setText(myEvent.getStartTime() + " - " + myEvent.getEndTime());

        eventHour = myView.findViewById(R.id.eventhour);
        eventHour.setText(Double.toString(myEvent.getHours()));

        duty = myView.findViewById(R.id.event_duty);
        duty.setText(checkNull(myEvent.getMyDuties()));

        location = myView.findViewById(R.id.event_location);
        location.setText(checkNull(myEvent.getLocation()));
    }

    private String checkNull(String temp){
        if(temp != null){
            if(temp.equals("")) return "N/A";
            else return temp;
        }
        return "N/A";
    }

}
