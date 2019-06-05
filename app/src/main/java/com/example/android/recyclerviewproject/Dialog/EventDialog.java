package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.example.android.recyclerviewproject.R;

public class EventDialog extends AppCompatDialogFragment {

    private EventDialogListener listener;
    private RelativeLayout service_layout;
    private TextView eventName;
    private int[] colorChoices;
    private TextView dateStart;
    private TextView duty;
    private TextView location;
    private int colorCode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.event_dialog, null);
        builder.setView(myView);
        initViews(myView);
        Dialog myDialog = builder.create();
        Window myWindow = myDialog.getWindow();
        myWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myWindow.getAttributes().windowAnimations = R.style.EventDialogTheme;
        return myDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (EventDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public void setColorCode(int i){
        colorCode = i;
        colorChoices = new RandomColor().getMyArray();
    }

    private void initViews(View myView){
        service_layout = myView.findViewById(R.id.event_color);
        service_layout.setBackgroundResource(colorChoices[colorCode]);
    }

    public interface EventDialogListener{
        void editEvent();
    }
}
