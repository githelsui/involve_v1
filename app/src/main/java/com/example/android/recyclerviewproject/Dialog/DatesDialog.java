package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;
import com.example.android.recyclerviewproject.R;

import static android.content.ContentValues.TAG;

public class DatesDialog extends AppCompatDialogFragment {

    private DateDialogListener listener;
    private EventDialog mainDialog;
    private TextView title;
    private DatePicker startDate, endDate;
    private RelativeLayout startView;
    private RelativeLayout endView;
    private RelativeLayout layout;
    private ServiceItem myItem;
    private int colorCode;
    private int[] colorChoices;
    private Context myContext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.date_dialog, null);
        builder.setView(myView);
        initViews(myView);
        final Dialog myDialog = builder.create();
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);
        Window myWindow = myDialog.getWindow();
        myWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myWindow.getAttributes().windowAnimations = R.style.EventDialogTheme;
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                ImageView closeBtn = myView.findViewById(R.id.closebtn);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openEventDialog();
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
            listener =(DateDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT EVENTDIALOGLISTENER");
        }
    }

    public void setItem(ServiceItem temp){ myItem = temp; }


    public void setColor(int i){
        colorCode = i;
        colorChoices = new RandomColor().getMyArray();
    }

    public void setCont(Context temp){ myContext = temp; }

    private void openEventDialog(){
        mainDialog = new EventDialog();
        try {
            FragmentManager fragmentManager = ((FragmentActivity) myContext).getSupportFragmentManager();
        } catch (ClassCastException e) {
            Log.e(TAG, "Can't get fragment manager");
        }
        mainDialog.setColorCode(colorCode);
        mainDialog.setService(myItem);
        mainDialog.setMyContext(myContext);
        mainDialog.show(getFragmentManager(), "Service Event");
    }

    private void initViews(View myView){
        title = myView.findViewById(R.id.edit_title);
        title.setText("Edit Dates");
        startDate = myView.findViewById(R.id.date_started);
        endDate = myView.findViewById(R.id.date_end);
        startView = myView.findViewById(R.id.start_dateview);
        endView = myView.findViewById(R.id.end_dateview);
        layout = myView.findViewById(R.id.main_dialog);
        layout.setBackgroundResource(colorChoices[colorCode]);
    }

    public interface DateDialogListener{
        void saveDates(String temp, int pos);
    }
}
