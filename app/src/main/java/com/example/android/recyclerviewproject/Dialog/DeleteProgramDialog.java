package com.example.android.recyclerviewproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.android.recyclerviewproject.R;
import com.example.android.recyclerviewproject.Custom_Object.RandomColor;

public class DeleteProgramDialog extends AppCompatDialogFragment {

    private DeleteProgramDialogListener listener;
    private RecyclerView.ViewHolder myV;

    public void setView(RecyclerView.ViewHolder view){
        myV = view;
    }

    public RecyclerView.ViewHolder getRecyclerView(){
        return myV;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.deleteprogram_dialog, null);
        builder.setView(myView)
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        listener.notifyChanges(myV);
                    }

                }) //when "Cancelled" do nothing
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        listener.removeProgram(myV);
                    }
                });
        Dialog dialogRes = builder.create();
        dialogRes.setCanceledOnTouchOutside(false);
        return dialogRes;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (DeleteProgramDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " IMPLEMENT DeleteProgramDialog");
        }
    }

    public interface DeleteProgramDialogListener{
        void removeProgram(RecyclerView.ViewHolder temp);
        void notifyChanges(RecyclerView.ViewHolder temp);
    }
}
