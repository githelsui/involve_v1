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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.R;

import java.util.Calendar;
import java.util.Locale;

public class DateDialog extends AppCompatDialogFragment {

    private DatePicker dateStart;
    private TextView mTitle;
    private DateDialogListener listener;
    private String mDate;
    private String mEnd;
    private Context myContext;
    private EditText mLoc;
    private EditText mName;
    private EditText mInfo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.serviceinfo_dialog, null);
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

    public void setMyContext(Context cont){
        myContext = cont;
    }


    public void passElements(String dateStart, String dateEnd){
        mDate = dateStart;
        mEnd = dateEnd;
    }

    private void showNextDialog(){
        //TODO show time dialog
    }

    private void setLayout(View myView){
        mName = myView.findViewById(R.id.eventname_text);
        mInfo = myView.findViewById(R.id.duties_info);
        mLoc = myView.findViewById(R.id.loc_info);
        mTitle = myView.findViewById(R.id.servicename);
        mTitle.setText("Service for\n" + title(mDate,mEnd));
    }

    private String checkTexts(){
        if(mName.getText().toString().equals("")) return getString(R.string.no_name);
        else return mName.getText().toString();
    }

    private String title(String start, String end){
        if(start.equals(end)) return start;
        else return start + " - " + end;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (DateDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT EVENTDIALOGLISTENER");
        }
    }

    public interface DateDialogListener{
        void passServeElements();
        //TODO create paramters for other service privates
    }
}
