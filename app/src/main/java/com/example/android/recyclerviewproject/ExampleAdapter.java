package com.example.android.recyclerviewproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<ExampleItem> myListAdapt;
    private OnItemClickListener mListener;
    private Context myCont;

    //Adapter Constructor: configures the info from private arraylist into the contents of the card
    public ExampleAdapter(ArrayList<ExampleItem> list, Context cont){
        myListAdapt = list;
        myCont = cont;
    }

    @NonNull
    @Override //passes Layout of the card (example_item.xml) to the Adapter
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater obj = LayoutInflater.from(viewGroup.getContext());
        View newView = obj.inflate(R.layout.example_item, viewGroup, false);
        ExampleViewHolder mySelf = new ExampleViewHolder(newView, mListener);
        return mySelf;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        ExampleItem obj =  myListAdapt.get(i);
        (exampleViewHolder.mTextView2).setText(Double.toString(obj.getHours()) + " ");

        if(obj.getProgram().length() > 13){
            int indexEnd = 13;
            for(int j = indexEnd; j < obj.getProgram().length(); j++){
                if(obj.getProgram().substring(i, i+1) == " ") indexEnd = j-1;
            }
            String temp = obj.getProgram().substring(0, indexEnd);
            exampleViewHolder.mTextView1.setText(temp + "...");
        }
        else (exampleViewHolder.mTextView1).setText(obj.getProgram());

        RandomColor colors = new RandomColor();
        int[] choices = colors.getMyArray();
        (exampleViewHolder.mCardView).setBackgroundResource(choices[obj.getMyColor()]);
    }

    @Override
    public int getItemCount() {
        return myListAdapt.size();
    }


    //using a button INSIDE a cardview requires an interface class
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    //custom function of ExampleAdapter
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

            //creates the contents of a card at initialization and connects them to respected xml contents
    public static class ExampleViewHolder extends RecyclerView.ViewHolder   {
                    public RelativeLayout mCardView, mBackground;
                    public TextView mTextView1; //TextView shows program name
                    public TextView mTextView2; //TextView shows hours done for that specific program
                    public ImageView mArrow;
                    private OnItemClickListener mListener;

                    public ExampleViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            //constructor for cardviewholder java class
            mCardView = itemView.findViewById(R.id.relative_layout);
            mBackground = itemView.findViewById(R.id.backgroundprog_layout);
            mTextView1 = itemView.findViewById(R.id.text_view1);
            mTextView2 = itemView.findViewById(R.id.text_view2);
            mArrow = itemView.findViewById(R.id.arrow_img);
            mListener = listener;
            arrowClicked();
        }

        private void arrowClicked(){
                        mArrow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(mListener != null){
                                    int pos = getAdapterPosition();
                                    if(pos != RecyclerView.NO_POSITION){
                                        mListener.onItemClick(pos); //calls the interface method from OnItemClickListener only IF
                                                                    //it is the position of the mArrow object
                                    }
                                }
                            }
                        });
        }
    }
}
