package com.example.android.recyclerviewproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private ArrayList<ServiceItem> myServiceList;
    private OnItemClickListener mListener;
    private ExampleItem obj;

    public ServiceAdapter(ArrayList<ServiceItem> list, ExampleItem temp) {
        obj = temp;
        myServiceList = list;
    }



    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater obj = LayoutInflater.from(viewGroup.getContext());
        View newView = obj.inflate(R.layout.example_item, viewGroup, false);
        ServiceViewHolder mySelf = new ServiceViewHolder(newView, mListener);
        return mySelf;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i) {
        ServiceItem temp = myServiceList.get(i);
        serviceViewHolder.mTextView1.setText("date");
        serviceViewHolder.mTextView2.setText(Double.toString(temp.getHours()) + " ");

        RandomColor colors = new RandomColor();
        int[] choices = colors.getMyArray();
        (serviceViewHolder.mCardView).setBackgroundResource(choices[obj.getMyColor()]);
    }

    @Override
    public int getItemCount() {
         return myServiceList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mCardView;
        public TextView mTextView1; //TextView shows a Date (month.day.year)
        public TextView mTextView2; //TextView shows hours done for that specific service
        public ImageView mArrow;    //button to be clicked for info regarding that service -> opens up to Dialog with Data
        private OnItemClickListener mListener;

        public ServiceViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.relative_layout);
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
