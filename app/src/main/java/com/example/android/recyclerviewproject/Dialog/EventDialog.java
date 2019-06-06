package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;
import com.example.android.recyclerviewproject.R;

public class EventDialog extends AppCompatDialogFragment {
    //TODO make editMode() function by dialog.setPositive to edit button

    private EventDialogListener listener;
    private RelativeLayout service_layout;
    private TextView eventName;
    private int[] colorChoices;
    private TextView dateStart;
    private TextView duty;
    private TextView eventHour;
    private TextView location;
    private ServiceItem myEvent;
    private int colorCode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.event_dialog, null);
        builder.setView(myView);
        initViews(myView);
        final Dialog myDialog = builder.create();
        myDialog.setCanceledOnTouchOutside(false);
        Window myWindow = myDialog.getWindow();
        myWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myWindow.getAttributes().windowAnimations = R.style.EventDialogTheme;
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ImageView closeBtn = myView.findViewById(R.id.closebtn);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (EventDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT EVENTDIALOGLISTENER");
        }
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
        eventName = myView.findViewById(R.id.event_name);
        eventName.setText(checkNull(myEvent.getServiceName()));
        dateStart = myView.findViewById(R.id.event_date);
        dateStart.setText(myEvent.getStartDate());
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

    public interface EventDialogListener{
        void editEvent();
    }
}
