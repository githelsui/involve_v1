package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.R;

public class EditProgram extends AppCompatDialogFragment {

    private ExampleItem myItem;
    private EditText mName;
    private TextView mHeader;
    private TextView mAdvisor;
    private EditText mHours;
    private EditText mRole;
    private EditProgramListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.editprog, null);
        builder.setView(myView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", null);
        setLayout(myView);
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
                        if(mName.getText().toString().equals("") && mHours.getText().toString().equals("")
                                && mRole.getText().toString().equals("") && mAdvisor.getText().toString().equals("")){
                            Animation shake = AnimationUtils.loadAnimation(myView.getContext(), R.anim.shake);
                            mName.startAnimation(shake);
                            mHours.startAnimation(shake);
                            mRole.startAnimation(shake);
                            mAdvisor.startAnimation(shake);
                            Toast msg = Toast.makeText(myView.getContext(), "No changes made", Toast.LENGTH_SHORT);
                            msg.show();
                        }
                        else{
                            if(!mName.getText().toString().equals("")) myItem.setProgramName(mName.getText().toString());
                            if(!mHours.getText().toString().equals("")) myItem.setInitialHr(Double.parseDouble(mHours.getText().toString()));
                            if(!mRole.getText().toString().equals("")) myItem.setMyRole(mRole.getText().toString());
                            if(!mAdvisor.getText().toString().equals("")) myItem.setAdvisor(mAdvisor.getText().toString());
                            listener.editProgram(myItem);
                            myDialog.dismiss();
                            Toast msg = Toast.makeText(myView.getContext(), "Changes saved", Toast.LENGTH_SHORT);
                            msg.show();
                        }
                    }
                });
            }
        });
        return  myDialog;
    }

    private void setLayout(View myView){
        mHeader = myView.findViewById(R.id.servlbl_dialog);
        mName = myView.findViewById(R.id.program_name);
        mAdvisor = myView.findViewById(R.id.advisorlbl);
        mHours = myView.findViewById(R.id.current_hours);
        mRole = myView.findViewById(R.id.position_lbl);
        mHeader.setText("Edit " + myItem.getProgram());
        mName.setHint("Program Name: " + myItem.getProgram());
        mHours.setHint("Initial Hours: " + Double.toString(myItem.getInitialHr()));
        if(!myItem.getRole().equals("")) mRole.setHint("Role: " + myItem.getRole());
        if(myItem.getAdvisor() != null) mAdvisor.setHint("Advisor: " + myItem.getAdvisor());
    }

    public void setMyItem(ExampleItem temp){
        myItem = temp;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (EditProgramListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "  IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public interface EditProgramListener{
        void editProgram(ExampleItem passed);
    }
}
