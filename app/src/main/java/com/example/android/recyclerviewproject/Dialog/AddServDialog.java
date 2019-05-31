package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //creates Dialog java class by taking info from getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //creates an object that helps configure Dialog java class to its correct xml file
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //creates the View in which we can access the Dialog xml file's contents
        final View myView = inflater.inflate(R.layout.layout_dialog, null);
        //Attaches java dialog onto its XML layout file (GUI)
        builder.setView(myView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", null);
        prgrmName = myView.findViewById(R.id.program_name);
        currHrs = myView.findViewById(R.id.current_hours);
        role = myView.findViewById(R.id.position_lbl);
        final Dialog myDialog = builder.create();
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
        double hrs;
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
