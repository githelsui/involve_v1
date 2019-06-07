package com.example.android.recyclerviewproject.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.recyclerviewproject.Custom_Object.ExampleItem;
import com.example.android.recyclerviewproject.R;
import com.example.android.recyclerviewproject.Custom_Object.RandomColor;
import com.example.android.recyclerviewproject.Custom_Object.ServiceItem;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private ArrayList<ServiceItem> myServiceList;
    private OnItemClickListener mListener;
    private ExampleItem obj;

    public ServiceAdapter(ArrayList<ServiceItem> list, ExampleItem temp) {
        obj = temp;
        //TODO CONVERT ARRAYLIST OF PARCEABLES INTO SERVICE ITEMS
        myServiceList = list;
    }



    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View newView = inflater.inflate(R.layout.service_item, viewGroup, false);
        ServiceViewHolder mySelf = new ServiceViewHolder(newView, mListener);
        return mySelf;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i) {
        ServiceItem temp = (ServiceItem) (myServiceList.get(i));
        if(temp.getMyName().length() > 13){
            int indexEnd = 13;
            for(int j = indexEnd; j < temp.getMyName().length(); j++){
                if(temp.getMyName().substring(i, i+1) == " ") indexEnd = j-1;
            }
            String msg = temp.getMyName().substring(0, indexEnd);
            serviceViewHolder.mTextView1.setText(msg + "...");
        }
        else (serviceViewHolder.mTextView1).setText(temp.getMyName());
        serviceViewHolder.mTextView2.setText(temp.getStartDate());
        serviceViewHolder.mHour.setText(Double.toString(temp.getHours()) + " hours");


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
        public TextView mHour;
        private OnItemClickListener mListener;

        public ServiceViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.relative_layout);
            mTextView1 = itemView.findViewById(R.id.text_view1);
            mHour = itemView.findViewById(R.id.hourslbl);
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
