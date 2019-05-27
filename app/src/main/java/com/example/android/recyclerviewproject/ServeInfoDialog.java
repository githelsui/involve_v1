package com.example.android.recyclerviewproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class ServeInfoDialog extends AppCompatDialogFragment {

    //TODO #2 Define all private vars of widgets and views from serviceinfo_dialog.xml
    private ServeInfoDialog listener;
    //private View viewUsedtoReceiveUserInput

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //creates Dialog java class by taking info from getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //creates an object that helps configure Dialog java class to its correct xml file
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //creates the View in which we can access the Dialog xml file's contents
        final View myView = inflater.inflate(R.layout.serviceinfo_dialog, null);

        //Attaches java dialog onto its XML layout file (GUI)
        builder.setView(myView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) { }
                }) //when "Cancelled" do nothing
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //collects data from the views and inputs them as a ServiceItem's intrinsic values
                       // String date = private variable.getText().toString()
                    }
                });

        //Initialize private view = findViewById(R.id.name_of_view)

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            //TODO #4 listener = (ServeInfoDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "  //  IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public interface ServeInfoDialogListener{
        //TODO #5 Override these functions (change parameters) in ProgramActivity.java after this entire class is complete
        void applyServiceText(String name, double hrs, String myRole); //sets views to the appropriate values/data passed by the parameters
        void saveServiceData(); //uses SharedPreferences to store Data permanently
    }
}
