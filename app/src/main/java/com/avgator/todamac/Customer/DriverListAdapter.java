package com.avgator.todamac.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avgator.todamac.R;

import java.util.ArrayList;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.MyViewHolder>{
    Context context;

    ArrayList<SeeDriverObject> list;

    public DriverListAdapter(Context context, ArrayList<SeeDriverObject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DriverListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.see_driver_item,parent,false);
        return new DriverListAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull DriverListAdapter.MyViewHolder holder, int position) {

        SeeDriverObject object =  list.get(position);
        holder.driverName.setText(object.getDriverName());
        holder.driverPhone.setText(object.getDriverPhone());
        holder.ratingBar.setRating(object.getRatingBar());
        holder.ratingBar.setIsIndicator(true);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView driverName, driverPhone;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            driverName = itemView.findViewById(R.id.etDriverName);
            driverPhone = itemView.findViewById(R.id.etDriverPhone);
            ratingBar = itemView.findViewById(R.id.rbDriverRate);


        }
    }
}
