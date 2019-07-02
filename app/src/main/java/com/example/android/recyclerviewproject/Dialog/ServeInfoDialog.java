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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.android.recyclerviewproject.Activity.ProgramActivity;
import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;
import com.example.android.recyclerviewproject.R;
import static android.content.ContentValues.TAG;

public class ServeInfoDialog extends AppCompatDialogFragment{
    private RelativeLayout endDateView;
    private RelativeLayout mainDialog;
    private RelativeLayout startDateView;
    private RelativeLayout startTimeView;
    private RelativeLayout endTimeView;
    private RelativeLayout hoursView;
    private RelativeLayout finalView;
    private Context myContext;
    private DatePicker dateStart;
    private DatePicker dateEnd;
    private TextView title;
    private TextView backBtn;
    private TimePicker timeStart;
    private ExampleItem myItem;
    private int[] colorChoices;
    private TimePicker timeEnd;
    private int startHr, endHr, startMin, endMin;
    private int startMonth, startDay, startYr, endMonth, endDay, endYr;
    private EditText mLoc;
    private EditText mName;
    private EditText mInfo;
    private EditText mHours;
    private double hrs;
    private Switch dialogSwitch;
    private ServeInfoDialogListener listener;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (ServeInfoDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public interface ServeInfoDialogListener{
        void saveNewService(ServiceItem item);
    }

    public void setColors(ExampleItem item, int[] arr){
        myItem = item;
        colorChoices = arr;
    }

    private void showEndDate(final TextView btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
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
                    Toast.makeText(myContext, "End date cannot exist before start date", Toast.LENGTH_LONG).show();
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEndTime();
                if(checkTimes() == false){
                    Animation shake = AnimationUtils.loadAnimation(myView.getContext(), R.anim.shake);
                    timeEnd.startAnimation(shake);
                    Toast.makeText(myContext, "End time cannot exist before start time", Toast.LENGTH_LONG).show();
                }
                else{
                    endTimeView.setVisibility(View.INVISIBLE);
                    showHoursView(btn, myDialog, myView);
                }
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

    private void showHoursView(final TextView btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        title.setText("Service for\n" + getDateInfo() + "\nfrom " + getStartTime() + " to " + getEndTime());
        title.setTextSize(28);
        mHours.setHint(getHrs() + "");
        hoursView.setVisibility(View.VISIBLE);
        hoursView.setAlpha(0f);
        hoursView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursView.setVisibility(View.INVISIBLE);
                checkHours();
                showFinalView(btn, myDialog, myView);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoursView.setVisibility(View.INVISIBLE);
                showEndTime(btn, myDialog, myView);
            }
        });
        dialogSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(myContext, "Overnight Event Selected", Toast.LENGTH_SHORT).show();
                    mHours.setHint(getOvernightHrs() + "");
                }
                else{
                    Toast.makeText(myContext, "Overnight Event Unselected", Toast.LENGTH_SHORT).show();
                    mHours.setHint(getHrs() + "");
                }
            }
        });
    }

    private double getOvernightHrs(){
        if(oneDay()) return getHrs();
        else{
            final Calendar c = Calendar.getInstance();
            int fullDays = 0;
            long startDate;
            long endDate;
            try{
                SimpleDateFormat format = new SimpleDateFormat("MMMMM dd, yyyy");
                Date date1 = format.parse(getStartDate());
                Date date2 = format.parse(getEndDate());
                startDate = date1.getTime();
                endDate = date2.getTime();
                fullDays = numDaysBetween(c, startDate, endDate)-2;
                if(fullDays < 0) fullDays = 0;
            }catch (ParseException e){
                System.out.println("parse exception!");
            }
            double start = 24 - (startHr + (startMin/ (double) 60));
            double end = endHr + (endMin/ (double) 60);
            double result = (fullDays * 24) + start + end;
            return  Math.round(result * 100.0) / 100.0;
        }
        //start july 1 at 6am or 6 hrs
        //--> starting hours = 24 - startHr
        //--> starting hours is 18

        //end july 2 at 9am or 9 hrs
        //ending hours is 9 hrs
    }

    private void checkHours(){
        if(mHours.getText().toString().equals("")) {
            double parsedVal = Double.parseDouble(mHours.getHint().toString());
            System.out.println("parsed val: " + parsedVal);
            if(parsedVal == getOvernightHrs()) hrs = getOvernightHrs();
            else if (parsedVal == getHrs()) hrs = getHrs();
         }
        else hrs = Double.parseDouble(mHours.getText().toString());
    }

    private void showFinalView(final TextView btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        title.setText("Service for\n" + getDateInfo() + "\nfrom " + getStartTime() + " to " + getEndTime());
        title.setTextSize(28);
        finalView.setVisibility(View.VISIBLE);
        finalView.setAlpha(0f);
        finalView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        btn.setText("SAVE");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalView.setVisibility(View.INVISIBLE);
                ServiceItem temp = new ServiceItem(hrs, getStartDate(), getEndDate(), mInfo.getText().toString(), checkTexts());
                temp.setLoc(mLoc.getText().toString());
                temp.setStartTime(getStartTime());
                temp.setEndTime(getEndTime());
                temp.setStartDatesInt(startMonth, startDay, startYr);
                temp.setEndDatesInt(endMonth, endDay, endYr);
                listener.saveNewService(temp);
                myDialog.dismiss();
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

    private String checkTexts(){
        if(mName.getText().toString().equals("")) return getString(R.string.no_name);
        else return mName.getText().toString();
    }

    private double getHrs(){
        final Calendar c = Calendar.getInstance();
        int numDays = 1;
        long startDate;
        long endDate;
        try{
            SimpleDateFormat format = new SimpleDateFormat("MMMMM dd, yyyy");
            Date date1 = format.parse(getStartDate());
            Date date2 = format.parse(getEndDate());
            startDate = date1.getTime();
            endDate = date2.getTime();
            numDays = numDaysBetween(c, startDate, endDate);
        }catch (ParseException e){
            System.out.println("parse exception!");
        }
        double num = (double)(numDays) * numHoursBetween();
        return Math.round(num * 100.0) / 100.0;
    }

    private int numDaysBetween(final Calendar c, final long fromTime, final long toTime) {
        int result = 0;
        if (toTime <= fromTime) return 1;

        c.setTimeInMillis(toTime);
        final int toYear = c.get(Calendar.YEAR);
        result += c.get(Calendar.DAY_OF_YEAR);

        c.setTimeInMillis(fromTime);
        result -= c.get(Calendar.DAY_OF_YEAR);

        while (c.get(Calendar.YEAR) < toYear) {
            result += c.getActualMaximum(Calendar.DAY_OF_YEAR);
            c.add(Calendar.YEAR, 1);
        }
        result += 1;
        return result;
    }

    private double numHoursBetween(){
        double totalMin = ((endHr * 60) + endMin) - ((startHr*60)+startMin);
        return totalMin / (double) 60;
    }

    private void showStartDate(final TextView btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        backBtn.setVisibility(View.INVISIBLE);
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
        if(oneDay()) return getStartDate();
        else return getStartDate() + " - " + getEndDate();
    }

    private boolean oneDay(){
        if(getStartDate().equals(getEndDate())) return true;
        else return false;
    }

    private String getStartTime(){
        int hr = timeStart.getCurrentHour();
        startHr = hr;

        System.out.println("start hour: " + startHr);

        String am_pm = (timeStart.getCurrentHour() < 12) ? "AM" : "PM";
        if(am_pm.equals("PM")) hr -= 12;
        if(hr == 0) hr = 12;
        startMin = timeStart.getCurrentMinute();
        String minutes = (timeStart.getCurrentMinute() < 10) ? "0" : "";
        return hr + ":" +  minutes + "" + timeStart.getCurrentMinute() + " " + am_pm;
    }

    private void setEndTime(){
        endHr = timeEnd.getCurrentHour();
        endMin = timeEnd.getCurrentMinute();
        System.out.println(endHr + ":" + endMin);
    }

    private void setStartTime(){
        startHr = timeStart.getCurrentHour();
        startMin = timeStart.getCurrentMinute();
    }

    private String getEndTime(){
        int hr = timeEnd.getCurrentHour();
        endHr = hr;
        String am_pm = (timeEnd.getCurrentHour() < 12) ? "AM" : "PM";
        if(am_pm.equals("PM")) hr -= 12;
        if(hr == 0) hr = 12;
        endMin = timeEnd.getCurrentMinute();
        String minutes = (timeEnd.getCurrentMinute() < 10) ? "0" : "";
        return hr + ":" + minutes + "" + timeEnd.getCurrentMinute() + " " + am_pm;
    }

    private boolean checkTimes(){
            setEndTime();
            setStartTime();
            if(endHr == startHr)
                if(endMin > startMin) return true;
                else if(endMin < startMin) return false;
                else if(endMin == startMin) return true;
                else return false;
            else if(endHr > startHr) return true;
            else if(endHr < startHr) return false;
            else return false;
    }

    private String getStartDate(){
        final Calendar c = Calendar.getInstance();
        c.set(dateStart.getYear(), dateStart.getMonth(), dateStart.getDayOfMonth(), 0, 0, 0);
        Date chosenDate = c.getTime();
        startMonth = chosenDate.getMonth();
        startDay = chosenDate.getDay();
        startYr = chosenDate.getYear();
        DateFormat df_medium_us = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        String stringStartDate = df_medium_us.format(chosenDate);
        return stringStartDate;
    }

    private String getEndDate(){
        final Calendar c = Calendar.getInstance();
        c.set(dateEnd.getYear(), dateEnd.getMonth(), dateEnd.getDayOfMonth(), 0, 0, 0);
        Date chosenDate = c.getTime();
        endMonth = chosenDate.getMonth();
        endDay = chosenDate.getDay();
        endYr = chosenDate.getYear();
        DateFormat df_medium_us = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
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
        timeStart = myView.findViewById(R.id.timestart);
        timeEnd = myView.findViewById(R.id.timeend);
        mainDialog.setBackgroundResource(colorChoices[myItem.getMyColor()]);
        mName = myView.findViewById(R.id.eventname_text);
        mInfo = myView.findViewById(R.id.duties_info);
        mLoc = myView.findViewById(R.id.loc_info);
        mHours = myView.findViewById(R.id.hoursedit_text);
        hoursView = myView.findViewById(R.id.hours_view);
        dialogSwitch = myView.findViewById(R.id.overnight_switch);
    }

}
