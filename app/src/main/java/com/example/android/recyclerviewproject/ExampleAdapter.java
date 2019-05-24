package com.example.android.recyclerviewproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<ExampleItem> myListAdapt;

            //creates the contents of a card at initialization and connects them to respected xml contents
    public static class ExampleViewHolder extends RecyclerView.ViewHolder   {
                    public TextView mTextView1;
                    public TextView mTextView2;
                    public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.text_view1);
            mTextView2 = itemView.findViewById(R.id.text_view2);
        }
    }

    //Adapter Constructor: configures the info from private arraylist into the contents of the card
    public ExampleAdapter(ArrayList<ExampleItem> list){
        myListAdapt = list;
    }

    @NonNull
    @Override //passes Layout of the card (example_item.xml) to the Adapter
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater obj = LayoutInflater.from(viewGroup.getContext());
        View newView = obj.inflate(R.layout.example_item, viewGroup, false);
        ExampleViewHolder mySelf = new ExampleViewHolder(newView);
        return mySelf;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        ExampleItem obj =  myListAdapt.get(i);

        //TODO #1 change int->string into DOUBLE->string using Double.toString(double)
        (exampleViewHolder.mTextView2).setText(Double.toString(obj.getHours()) + " ");
        (exampleViewHolder.mTextView1).setText(obj.getProgram());
    }

    @Override
    public int getItemCount() {
        return myListAdapt.size();
    }
}
