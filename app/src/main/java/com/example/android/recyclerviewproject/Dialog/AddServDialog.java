package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.android.recyclerviewproject.R;
import com.example.android.recyclerviewproject.Custom_Object.RandomColor;

public class AddServDialog extends AppCompatDialogFragment {

    //TODO #1 Clean up addprogram dialog using private helper methods
    private EditText prgrmName;
    private EditText currHrs;
    private EditText role;
    private AddServeDialogListener listener;
//
//    Dialog myDialog = builder.create();
//    Window myWindow = myDialog.getWindow();
//        myWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myWindow.getAttributes().windowAnimations = R.style.EventDialogTheme;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(myView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", null);
        prgrmName = myView.findViewById(R.id.program_name);
        currHrs = myView.findViewById(R.id.current_hours);
        role = myView.findViewById(R.id.position_lbl);
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
                           if(prgrmName.getText().toString().equals("")){
                               Animation shake = AnimationUtils.loadAnimation(myView.getContext(), R.anim.shake);
                               prgrmName.startAnimation(shake);
                               Toast msg = Toast.makeText(myView.getContext(), "Program Name is Required", Toast.LENGTH_SHORT);
                               msg.show();
                           }
                           else{
                               String name = prgrmName.getText().toString();
                               double hrs = checkHours();
                               String myRole = role.getText().toString();
                               RandomColor rand = new RandomColor();
                               listener.applyText(name, hrs, myRole, rand);
                               listener.saveData();
                               myDialog.dismiss();
                           }

                       }
                   });
            }

        });
        return myDialog;
    }

    private double checkHours(){
        if(currHrs.getText().toString().equals("")) return 0;
        else return Double.parseDouble(currHrs.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (AddServeDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public interface AddServeDialogListener{
        void applyText(String name, double hrs, String myRole, RandomColor picker);
        void saveData();
    }
}
