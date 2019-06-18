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
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.recyclerviewproject.Activity.ProgramActivity;
import com.example.android.recyclerviewproject.R;
import static android.content.ContentValues.TAG;

public class ServeInfoDialog extends AppCompatDialogFragment{
    //TODO maybe use a dateview instead of editText for startDate and change entire dialog layout using FRAGMENTS
     private ServeInfoDialogListener listener;
    private DateDialog dateDialog;
    private Context myContext;
    private DatePicker dateStart;
    private TextView endLabel;
    private DatePicker dateEnd;
    private TextView dateLabel;
    private TextView title;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.servicedates, null);
        builder.setView(myView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", null);
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
                final Button nextBtn = ((AlertDialog) myDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            //TODO use DatepICKER instead of edit text values
                             // listener.applyServiceText(0.0, defaultDates(), defaultDates(), mInfo.getText().toString(), checkTexts());
                              showEndDate(nextBtn, myDialog, myView);
                    }
                });
            }
        });
        return myDialog;
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

    private void showEndDate(Button btn, final Dialog myDialog, final View myView){
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        title.setText("Service for " + getStartDate());
        title.setTextSize(28);
        dateLabel.setVisibility(View.INVISIBLE);
        dateStart.setVisibility(View.INVISIBLE);
        endLabel.setAlpha(0f);
        dateEnd.setAlpha(0f);
        dateEnd.setVisibility(View.VISIBLE);
        endLabel.setVisibility(View.VISIBLE);
        endLabel.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        dateEnd.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDates() == false){
                    Animation shake = AnimationUtils.loadAnimation(myView.getContext(), R.anim.shake);
                    dateEnd.startAnimation(shake);
                    endLabel.startAnimation(shake);
                    Toast.makeText(myContext, "End date cannot exist before start date", Toast.LENGTH_SHORT).show();
                }
                else{
                    myDialog.dismiss();
                    showNextDialog();
                }
            }
        });
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

    private void showNextDialog(){
        dateDialog = new DateDialog();
        try {
            FragmentManager fragmentManager = ((FragmentActivity) myContext).getSupportFragmentManager();
        } catch (ClassCastException e) {
            Log.e(TAG, "Can't get fragment manager");
        }
        dateDialog.passElements(getStartDate(), getEndDate());
        dateDialog.show(getFragmentManager(), "Additional Info");
    }

    public void setViews(View myView){
        dateStart = myView.findViewById(R.id.date_started);
        dateLabel = myView.findViewById(R.id.datestart_lbl);
        dateEnd = myView.findViewById(R.id.date_end);
        endLabel = myView.findViewById(R.id.date_endlbl);
        title = myView.findViewById(R.id.servicename);
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
