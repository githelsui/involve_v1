package com.example.android.recyclerviewproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ServeInfoDialog extends AppCompatDialogFragment {

     private ServeInfoDialogListener listener;
    private EditText mStartDate;
    private EditText mEndDate;
    private EditText mHours;
    private EditText mInfo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //creates Dialog java class by taking info from getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //creates an object that helps configure Dialog java class to its correct xml file
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //creates the View in which we can access the Dialog xml file's contents
        final View myView = inflater.inflate(R.layout.serviceinfo_dialog, null);

        //Attaches java dialog onto its XML layout file (GUI)
        builder.setView(myView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) { }
                }) //when "Cancelled" do nothing
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String startDate = mStartDate.getText().toString();
                        String endDate = mEndDate.getText().toString();
                        double hrs = Double.parseDouble(mHours.getText().toString());
                        String info = mInfo.getText().toString();

                        listener.applyServiceText(hrs, startDate, endDate, info);
                        //listener.saveServiceData();
                    }
                });
        mStartDate = myView.findViewById(R.id.date_started);
        mEndDate = myView.findViewById(R.id.date_end);
        mHours = myView.findViewById(R.id.ind_hours);
        mInfo = myView.findViewById(R.id.duties_info);
        return builder.create();
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

    public interface ServeInfoDialogListener{
        //TODO #5 Override these functions (change parameters) in ProgramActivity.java after this entire class is complete
        void applyServiceText(double hours, String startDate, String endDate, String duties); //CREATE A SERVICEITEM OBJECT -> ADDS TO PROGRAM'S LIST OF SERVICES
        void saveServiceData(); //uses SharedPreferences to store Data permanently
    }
}
