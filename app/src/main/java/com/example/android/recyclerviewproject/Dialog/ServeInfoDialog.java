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
import android.widget.EditText;

import com.example.android.recyclerviewproject.R;

public class ServeInfoDialog extends AppCompatDialogFragment {

    //TODO #1 Clean up addServiceDialog. Remove mEndDate. Add a check or switch for boolean ifComplete()
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
                    }
                });
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
        mStartDate = myView.findViewById(R.id.date_started);
        mStartDate.setHint(simpleDate.format(currentDate));
        mEndDate = myView.findViewById(R.id.date_end);
        mEndDate.setHint(mStartDate.getHint());
        mHours = myView.findViewById(R.id.ind_hours);
        mHours.setHint("0.00");
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

    public interface ServeInfoDialogListener {
        void applyServiceText(double hours, String startDate, String endDate, String duties);
    }
}
