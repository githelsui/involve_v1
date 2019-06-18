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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;
import com.example.android.recyclerviewproject.R;

public class EditEventDialog extends AppCompatDialogFragment {

    private EditEventListener listener;
    private TextView mHeader;
    private RelativeLayout mLayout;
    private EditText mName;
    private EditText mDate;
    private EditText mHour;
    private EditText mDuty;
    private EditText mLoc;
    private ServiceItem myItem;
    private int[] colorChoices;
    private int colorCode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.editevent, null);
        builder.setView(myView);
        setLayout(myView);
        final Dialog myDialog = builder.create();
        myDialog.setCanceledOnTouchOutside(false);
        Window myWindow = myDialog.getWindow();
        myWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myWindow.getAttributes().windowAnimations = R.style.EventDialogTheme;
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button cancelBtn = myView.findViewById(R.id.cancel_edit);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                Button saveBtn = myView.findViewById(R.id.save_event);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mName.getText().toString().equals("") && mDate.getText().toString().equals("")
                                && mHour.getText().toString().equals("") && mDuty.getText().toString().equals("") && mLoc.getText().toString().equals("")) {
                            Animation shake = AnimationUtils.loadAnimation(myView.getContext(), R.anim.shake);
                            mName.startAnimation(shake);
                            mDate.startAnimation(shake);
                            mDuty.startAnimation(shake);
                            mHour.startAnimation(shake);
                            mLoc.startAnimation(shake);
                            Toast msg = Toast.makeText(myView.getContext(), "No changes made", Toast.LENGTH_SHORT);
                            msg.show();
                              }
                        else
                            {
                                if(!mName.getText().toString().equals("")) myItem.setName(mName.getText().toString());
                                if(!mHour.getText().toString().equals("")) myItem.setHours(Double.parseDouble(mHour.getText().toString()));
                                if(!mDate.getText().toString().equals("")) myItem.setDate(mDate.getText().toString());
                                if(!mDuty.getText().toString().equals("")) myItem.setDuty(mDuty.getText().toString());
                                if(!mLoc.getText().toString().equals("")) myItem.setLoc(mLoc.getText().toString());
                                listener.saveData(myItem);
                                myDialog.dismiss();
                                Toast msg = Toast.makeText(myView.getContext(), "Changes saved", Toast.LENGTH_SHORT);
                                msg.show();
                            }
                        }
                });
            }
        });
        return myDialog;
    }

    public void setColorCode(int i){
        colorCode = i;
        colorChoices = new RandomColor().getMyArray();
    }
    public void setService(ServiceItem temp){
        myItem = temp;
    }

    private void setLayout(View myView){
        mLayout = myView.findViewById(R.id.event_color);
        mHeader = myView.findViewById(R.id.event_name);
        mName = myView.findViewById(R.id.edit_name);
        mDate = myView.findViewById(R.id.event_date);
        mHour = myView.findViewById(R.id.eventhour);
        mDuty = myView.findViewById(R.id.event_duty);
        mLoc = myView.findViewById(R.id.event_location);
        mLayout.setBackgroundResource(colorChoices[colorCode]);
        mHeader.setText("Edit " + myItem.getMyName());
        mName.setHint(checkNull(myItem.getMyName()));
        mDate.setHint(checkNull(myItem.getStartDate()));
        mHour.setHint(checkNull(Double.toString(myItem.getHours())));
        mDuty.setHint(checkNull(myItem.getMyDuties()));
        mLoc.setHint(checkNull(myItem.getLocation()));
    }

    private String checkNull(String temp){
        if(temp == null || temp.equals("")) return getString(R.string.n_a);
        else return temp;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (EditEventListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public interface EditEventListener{
        void saveData(ServiceItem item);
    }
}
