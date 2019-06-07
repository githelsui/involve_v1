package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.recyclerviewproject.R;

public class ServeInfoDialog extends AppCompatDialogFragment {

    //TODO #1 Clean up addServiceDialog. Remove mEndDate. Add a check or switch for boolean ifComplete()
    //TODO maybe use a dateview instead of editText for startDate and change entire dialog layout using FRAGMENTS
     private ServeInfoDialogListener listener;
    private EditText mStartDate;
    private EditText mHours;
    private EditText mName;
    private EditText mInfo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.serviceinfo_dialog, null);
        builder.setView(myView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", null);
        setViews(myView);
        final Dialog myDialog = builder.create();
        Window myWindow = myDialog.getWindow();
        myWindow.getAttributes().windowAnimations = R.style.DialogSlide;
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button saveBtn = ((AlertDialog) myDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mStartDate.getText().toString().equals("")){
                            Animation shake = AnimationUtils.loadAnimation(myView.getContext(), R.anim.shake);
                            mStartDate.startAnimation(shake);
                            Toast msg = Toast.makeText(myView.getContext(), "Date is Required", Toast.LENGTH_SHORT);
                            msg.show();
                        }
                        else{
                            String date = mStartDate.getText().toString();
                            double hrs = checkHours();
                            String info = mInfo.getText().toString();
                            listener.applyServiceText(hrs,date, "end", info, checkTexts());
                            myDialog.dismiss();
                        }
                    }
                });
            }
        });
        return myDialog;
    }

    private double checkHours(){
        if(mHours.getText().toString().equals("")) return 0;
        else return Double.parseDouble(mHours.getText().toString());
    }

    private String checkTexts(){
        if(mName.getText().toString().toString().equals("")) return getString(R.string.no_name);
        else return mName.getText().toString();
    }

    public void setViews(View myView){
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
        mName = myView.findViewById(R.id.eventname_text);
        mStartDate = myView.findViewById(R.id.date_started);
        mStartDate.setHint(simpleDate.format(currentDate));
        mHours = myView.findViewById(R.id.ind_hours);
        mHours.setHint(getString (R.string.initialHrs));
        mInfo = myView.findViewById(R.id.duties_info);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
           listener = (ServeInfoDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "  IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public interface ServeInfoDialogListener {
        void applyServiceText(double hours, String startDate, String endDate, String duties, String name);
    }
}
