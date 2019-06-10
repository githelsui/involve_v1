package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.recyclerviewproject.R;

public class ServeInfoDialog extends AppCompatDialogFragment {

    //TODO #1 Clean up addServiceDialog. Remove mEndDate. Add a check or switch for boolean ifComplete()
    //TODO maybe use a dateview instead of editText for startDate and change entire dialog layout using FRAGMENTS
     private ServeInfoDialogListener listener;
    private DatePicker mStartDate;
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
                .setPositiveButton("Next", null);
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
                            //TODO use DatepICKER instead of edit text values
                            //TODO select "NEXT" -> another dialog opens with date values (create second dialog with an interface implemented in THIS class)
                            double hrs = checkHours();
                            String info = mInfo.getText().toString();
                            listener.applyServiceText(0.0, getDateString(), "end", "info", "name");
                            myDialog.dismiss();
                    }
                });
            }
        });
        return myDialog;
    }

    private String getDateString(){
        final Calendar c = Calendar.getInstance();
        return c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + mStartDate.getDayOfMonth() + ", " + mStartDate.getYear();
        //c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
    }

//    private boolean checkDate(View myView){
//        Animation shake = AnimationUtils.loadAnimation(myView.getContext(), R.anim.shake);
//        String date = "";
//        if(date.equals("")){
//            mStartDate.startAnimation(shake);
//            Toast msg = Toast.makeText(myView.getContext(), "Date is Required", Toast.LENGTH_SHORT);
//            msg.show();
//            return false;
//        }
//        int slashCount = 0;
//        for(int i = 0; i < date.length(); i++){
//            if(date.substring(i, i+1).equals("/")) slashCount++;
//        }
//        if(slashCount != 2){
//            mStartDate.startAnimation(shake);
//            Toast msg = Toast.makeText(myView.getContext(), "Follow the format mm/dd/yyyy", Toast.LENGTH_SHORT);
//            msg.show();
//            return false;
//        }
//        if (checkContents(myView, date) == false){
//            mStartDate.startAnimation(shake);
//            Toast msg = Toast.makeText(myView.getContext(), "Follow wwwthe format mm/dd/yyyy", Toast.LENGTH_SHORT);
//            msg.show();
//            return false;
//        }
//        return true;
//    }

//    private boolean checkContents(View myView, String temp){
//        boolean marker = false;
//        int counter = 0;
//        for(int i = 0; i < temp.length(); i++){
//            if(marker == false && !temp.substring(i, i+1).equals("/")){
//                marker = false;
//                counter++;
//            }
//            if(marker == true && !temp.substring(i, i+1).equals("/")){
//                marker = false;
//                counter = 0;
//            }
//            if(temp.substring(i, i+1).equals("/")) marker = true;
//            if(counter > 3) {
//                return false;
//            }
//        }
//        return  true;
//    }

    private double checkHours(){
        if(mHours.getText().toString().equals("")) return 0;
        else return Double.parseDouble(mHours.getText().toString());
    }

    private String checkTexts(){
        if(mName.getText().toString().equals("")) return getString(R.string.no_name);
        else return mName.getText().toString();
    }

    public void setViews(View myView){
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
       // mName = myView.findViewById(R.id.eventname_text);
        mStartDate = myView.findViewById(R.id.date_started);
        //mStartDate.setAutofillHints(simpleDate.format(currentDate));
        //mHours = myView.findViewById(R.id.ind_hours);
        //mHours.setHint(getString (R.string.initialHrs));
        //mInfo = myView.findViewById(R.id.duties_info);
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
