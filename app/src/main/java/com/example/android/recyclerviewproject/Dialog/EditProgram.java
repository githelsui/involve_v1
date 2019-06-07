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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.R;

public class EditProgram extends AppCompatDialogFragment {

    private ExampleItem myItem;
    private EditText mName;
    private TextView mHeader;
    private EditText mHours;
    private EditText mRole;
    private EditProgramListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.layout_dialog, null);
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
                        myDialog.dismiss();
                    }
                });
            }
        });
        return  myDialog;
    }

    private void setLayout(View myView){
        mHeader = myView.findViewById(R.id.servlbl_dialog);
        mName = myView.findViewById(R.id.program_name);
        mHours = myView.findViewById(R.id.current_hours);
        mRole = myView.findViewById(R.id.position_lbl);
        mHeader.setText("Edit " + myItem.getProgram());
        mName.setHint("Program Name: " + myItem.getProgram());
        mHours.setHint("Initial Hours: " + Double.toString(myItem.getInitialHr()));
        if(!myItem.getRole().equals("")) mRole.setHint("Role: " + myItem.getRole());

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
