package com.avgator.todamac.Driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avgator.todamac.R;

import java.util.ArrayList;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.MyViewHolder> {

    Context context;

    ArrayList<Driver> list;

    public DriverListAdapter(Context context, ArrayList<Driver> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.driver_item,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Driver driver =  list.get(position);
        holder.nameTextView.setText(driver.getName());
        holder.licenseNumberTextView.setText(driver.getLicenseId());
        holder.phoneNumberTextView.setText(driver.getPhoneNumber());
        holder.addressTextView.setText(driver.getAddress());
        holder.plateNumberTextView.setText(driver.getPlateNumber());
        holder.emialTextView.setText(driver.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView, licenseNumberTextView, phoneNumberTextView, addressTextView, plateNumberTextView, emialTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.driver_name);
            licenseNumberTextView = itemView.findViewById(R.id.driver_license_number);
            phoneNumberTextView = itemView.findViewById(R.id.driver_phone_number);
            addressTextView = itemView.findViewById(R.id.driver_address);
            plateNumberTextView = itemView.findViewById(R.id.driver_plate_number);
            emialTextView = itemView.findViewById(R.id.driver_email);

        }
    }

        }

