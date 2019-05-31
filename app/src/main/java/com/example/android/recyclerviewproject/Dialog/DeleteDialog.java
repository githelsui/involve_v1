package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.example.android.recyclerviewproject.R;

public class DeleteDialog extends AppCompatDialogFragment {

    private ExampleItem myItem;
    private DeleteDialogListener listener;

    public void setMyItem(ExampleItem item){
        myItem = item;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //creates Dialog java class by taking info from getActivity()
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //creates an object that helps configure Dialog java class to its correct xml file
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //creates the View in which we can access the Dialog xml file's contents
        final View myView = inflater.inflate(R.layout.deleteprogram_dialog, null);

        //Attaches java dialog onto its XML layout file (GUI)
        builder.setView(myView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) { }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        listener.deleteItem(myItem.getPosition());
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (DeleteDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT ADDSERVEDIALOGLISTENER");
        }
    }

    public interface DeleteDialogListener{
        void deleteItem(int pos);
    }
}
