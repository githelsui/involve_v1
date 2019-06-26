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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;
import com.example.android.recyclerviewproject.R;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class NameDialog extends AppCompatDialogFragment{

    private NameDialogListener listener;
    private EventDialog mainDialog;
    private TextView title;
    private EditText input;
    private RelativeLayout layout;
    private ServiceItem myItem;
    private int colorCode;
    private int[] colorChoices;
    private Context myContext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View myView = inflater.inflate(R.layout.edit_text_dialog, null);
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
                TextView saveBtn = myView.findViewById(R.id.savebtm);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String name = (input.getText().toString().equals("")) ? "Untitled Event" : input.getText().toString();
                        listener.saveName(name, myItem.getPos());
                        openEventDialog();
                    }
                });
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
            listener =( NameDialogListener) context;
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
            title.setText("Edit Name");
            input = myView.findViewById(R.id.input);
            input.setText(myItem.getMyName());
            layout = myView.findViewById(R.id.main_dialog);
            layout.setBackgroundResource(colorChoices[colorCode]);
    }

    public interface NameDialogListener{
        void saveName(String temp, int pos);
    }
}
