package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.text.DateFormat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.android.recyclerviewproject.Activity.ProgramActivity;
import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.R;
import static android.content.ContentValues.TAG;

public class ServeInfoDialog extends AppCompatDialogFragment{
    private RelativeLayout endDateView;
    private RelativeLayout mainDialog;
    private RelativeLayout startDateView;
    private RelativeLayout startTimeView;
    private RelativeLayout endTimeView;
    private RelativeLayout finalView;
    private Context myContext;
    private DatePicker dateStart;
    private DatePicker dateEnd;
    private TextView title;
    private TextView startTimeTitle;
    private TextView backBtn;
    private TimePicker timeStart;
    private ExampleItem myItem;
    private int[] colorChoices;
    private TimePicker timeEnd;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.serviceinfo_dialog, null);
        builder.setView(myView);
        setViews(myView);
        final Dialog myDialog = builder.create();
        Window myWindow = myDialog.getWindow();
        myWindow.getAttributes().windowAnimations = R.style.DialogSlide;
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
               // final Button nextBtn = ((AlertDialog) myDialog).getButton(AlertDialog.BUTTON_POSITIVE);
               final TextView nextBtn = myView.findViewById(R.id.continue_btn);
                ImageView closeBtn = myView.findViewById(R.id.closebtn);
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEndDate(nextBtn, myDialog, myView);
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

    public void setColors(ExampleItem item, int[] arr){
        myItem = item;
        colorChoices = arr;
    }

    private void showEndDate(final TextView btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        title.setText("Service for " + getStartDate());
        title.setTextSize(32);
        startDateView.setVisibility(View.INVISIBLE);
        endDateView.setVisibility(View.VISIBLE);
        endDateView.setAlpha(0f);
        endDateView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        TextView backBtn = myView.findViewById(R.id.back_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDates() == false){
                    Animation shake = AnimationUtils.loadAnimation(myView.getContext(), R.anim.shake);
                    dateEnd.startAnimation(shake);
                    Toast.makeText(myContext, "End date cannot exist before start date", Toast.LENGTH_SHORT).show();
                }
                else{
                    endDateView.setVisibility(View.INVISIBLE);
                    showStartTime(btn, myDialog, myView);
                }
            }
        });
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    endDateView.setVisibility(View.INVISIBLE);
                    showStartDate(btn, myDialog, myView);
            }
        });
    }

    private void showStartTime(final TextView btn, final Dialog myDialog, final View myView){
//        dateDialog = new DateDialog();
//        try {
//            FragmentManager fragmentManager = ((FragmentActivity) myContext).getSupportFragmentManager();
//        } catch (ClassCastException e) {
//            Log.e(TAG, "Can't get fragment manager");
//        }
//        dateDialog.passElements(getStartDate(), getEndDate());
//        dateDialog.show(getFragmentManager(), "Additional Info");
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        title.setText("Service for\n" + getDateInfo());
        title.setTextSize(28);
        endDateView.setVisibility(View.INVISIBLE);
        startTimeView.setVisibility(View.VISIBLE);
        startTimeView.setAlpha(0f);
        startTimeView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startTimeView.setVisibility(View.INVISIBLE);
                    showEndTime(btn, myDialog, myView);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeView.setVisibility(View.INVISIBLE);
                showEndDate(btn, myDialog, myView);
            }
        });
    }

    private void showEndTime(final TextView btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        title.setText("Service for\n" + getDateInfo());
        title.setTextSize(28);
        endTimeView.setVisibility(View.VISIBLE);
        endTimeView.setAlpha(0f);
        endTimeView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        startTimeTitle.setText("Start Time at " + getStartTime());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimeView.setVisibility(View.INVISIBLE);
                showFinalView(btn, myDialog, myView);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimeView.setVisibility(View.INVISIBLE);
                showStartTime(btn, myDialog, myView);
            }
        });
    }

    private void showFinalView(final TextView btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        title.setText("Service for\n" + getDateInfo());
        title.setTextSize(28);
        finalView.setVisibility(View.VISIBLE);
        finalView.setAlpha(0f);
        finalView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalView.setVisibility(View.INVISIBLE);
                //call listener.saveService and override in programactivity.java
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalView.setVisibility(View.INVISIBLE);
                showEndTime(btn, myDialog, myView);
            }
        });
    }

    private void showStartDate(final TextView btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        title.setText("Add New Service");
        title.setTextSize(35);
        endDateView.setVisibility(View.INVISIBLE);
        startDateView.setVisibility(View.VISIBLE);
        startDateView.setAlpha(0f);
        startDateView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startDateView.setVisibility(View.INVISIBLE);
                    showEndDate(btn, myDialog, myView);
            }
        });
        backBtn.setVisibility(View.INVISIBLE);
    }

    public void setMyContext(Context cont){
        myContext = cont;
    }

    private String defaultDates(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        String dateToStr = format.format(today);
        return dateToStr;
    }

    private String getDateInfo(){
        if(getStartDate().equals(getEndDate())) return getStartDate();
        else return getStartDate() + " - " + getEndDate();
    }

    private String getStartTime(){
        int hr = timeStart.getCurrentHour();
        String am_pm = (timeStart.getCurrentHour() < 12) ? "AM" : "PM";
        if(am_pm.equals("PM")) hr -= 12;
        if(hr == 0) hr = 12;
        return hr + ":" + timeStart.getCurrentMinute() + " " + am_pm;
    }

    private String getEndTime(){
        final Calendar c = Calendar.getInstance();
        int hr = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        String am_pm = (hr < 12) ? "AM" : "PM";
        return Integer.toString(hr) + ":" + Integer.toString(min) + am_pm;
    }

    private String getStartDate(){
        final Calendar c = Calendar.getInstance();
        c.set(dateStart.getYear(), dateStart.getMonth(), dateStart.getDayOfMonth(), 0, 0, 0);
        Date chosenDate = c.getTime();
        DateFormat df_medium_us = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        String stringStartDate = df_medium_us.format(chosenDate);
        return stringStartDate;
    }

    private String getEndDate(){
        final Calendar c = Calendar.getInstance();
        c.set(dateEnd.getYear(), dateEnd.getMonth(), dateEnd.getDayOfMonth(), 0, 0, 0);
        Date chosenDate = c.getTime();
        DateFormat df_medium_us = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        String stringStartDate = df_medium_us.format(chosenDate);
        return stringStartDate;
    }

    private boolean checkDates(){
        Date date1;
        Date date2;
        try{
            SimpleDateFormat format = new SimpleDateFormat("MMMMM dd, yyyy");
            date1 = format.parse(getStartDate());
            date2 = format.parse(getEndDate());
            if(date1.after(date2)) return false;
            else return true;
        }catch (ParseException e){
            System.out.println("pase exception!");
        }
        return  true;
    }

    public void setViews(View myView){
        mainDialog = myView.findViewById(R.id.main_dialog);
        endDateView = myView.findViewById(R.id.end_dateview);
        startDateView = myView.findViewById(R.id.start_dateview);
        dateStart = myView.findViewById(R.id.date_started);
        dateEnd = myView.findViewById(R.id.date_end);
        title = myView.findViewById(R.id.servicename);
        startTimeView = myView.findViewById(R.id.starttime_view);
        endTimeView = myView.findViewById(R.id.endtime_view);
        backBtn = myView.findViewById(R.id.back_btn);
        finalView = myView.findViewById(R.id.last_view);
        startTimeTitle = myView.findViewById(R.id.startinfo);
        timeStart = myView.findViewById(R.id.timestart);
        timeEnd = myView.findViewById(R.id.timeend);
        mainDialog.setBackgroundResource(colorChoices[myItem.getMyColor()]);
    }

}
