package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.android.recyclerviewproject.R;

public class TimeDialog extends AppCompatDialogFragment {

    private TextView mTitle;
    private TextView dateInfo;
    private TextView timeStartLbl;
    private TimePicker startTime;
    private TextView timeEndLbl;
    private TimePicker endTime;
    private TextView timeInfo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.timedialog, null);
        builder.setView(myView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", null);
        final Dialog myDialog = builder.create();
        setLayout(myView);
        myDialog.setCanceledOnTouchOutside(false);
        Window myWindow = myDialog.getWindow();
        //myWindow.getAttributes().windowAnimations = R.style.DialogSlide;
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button nextBtn = ((AlertDialog) myDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                        showNextDialog();
                    }
                });
            }
        });
        return myDialog;
    }

    private void showNextDialog(){
        //TODO show time dialog
    }

    public void passElements(String dateStart, String dateEnd, String name, String duty, String loc){

    }

    private void setLayout(View myView){
       mTitle = myView.findViewById(R.id.servicename);
       //dateInfo = myView.findViewById(R.id.dateinfo);
       timeStartLbl = myView.findViewById(R.id.timestart_lbl);
       startTime = myView.findViewById(R.id.timestart);
       timeEndLbl = myView.findViewById(R.id.time_endlbl);
       endTime = myView.findViewById(R.id.timeend);
       timeInfo = myView.findViewById(R.id.startinfo);
    }
}
