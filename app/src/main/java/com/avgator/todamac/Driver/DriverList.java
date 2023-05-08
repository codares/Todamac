package com.avgator.todamac.Driver;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avgator.todamac.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriverList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ArrayList<Driver> list;
    private DriverListAdapter mAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);

        // Get references to UI elements
        recyclerView = findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a reference to the "drivers" node in the Firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child("Drivers");

        // Initialize driver list and adapter
        list = new ArrayList<>();
        mAdapter = new DriverListAdapter(this, list);
        recyclerView.setAdapter(mAdapter);


        // Retrieve all drivers and their information
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot driverSnapshot : dataSnapshot.getChildren()) {
                        //Map<String, Object> map = (Map<String, Object>) driverSnapshot.getValue();

                        String fullName = driverSnapshot.child("name").getValue(String.class);
                        String licenseId = driverSnapshot.child("licenseId").getValue(String.class);
                        String phoneNum = driverSnapshot.child("phoneNumber").getValue(String.class);
                        String address = driverSnapshot.child("address").getValue(String.class);
                        String plateNumber = driverSnapshot.child("plateNumber").getValue(String.class);
                        String email = driverSnapshot.child("email").getValue(String.class);

                        Driver driver = new Driver(fullName, licenseId, phoneNum, address, plateNumber, email);

                        //Driver driver = driverSnapshot.getValue(Driver.class);
                        list.add(driver);

                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}