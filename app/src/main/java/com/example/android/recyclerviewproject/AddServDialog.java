package com.example.android.recyclerviewproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddServDialog extends AppCompatDialogFragment {

    private EditText prgrmName;
    private EditText currHrs;
    private EditText role;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //creates Dialog java class by taking info from getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //creates an object that helps configure Dialog java class to its correct xml file
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //creates the View in which we can access the Dialog xml file's contents
        View myView = inflater.inflate(R.layout.layout_dialog, null);

        //Attaches java dialog onto its XML layout file (GUI)
        builder.setView(myView)
                .setTitle("Add New Service")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        prgrmName = myView.findViewById(R.id.program_name);
        currHrs = myView.findViewById(R.id.current_hours);
        role = myView.findViewById(R.id.position_lbl);

        //returns a Dialog View
        return builder.create();
    }
}
