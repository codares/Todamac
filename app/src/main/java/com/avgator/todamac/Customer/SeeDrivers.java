package com.avgator.todamac.Customer;

import android.os.Bundle;
import android.util.Log;

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
import java.util.Map;

public class SeeDrivers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ArrayList<SeeDriverObject> list;
    private DriverListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_drivers);

        // Get references to UI elements
        recyclerView = findViewById(R.id.list_see_driver);
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
            String driverName;
            String driverPhone;
            Float mRatingBar;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot driverSnapshot : dataSnapshot.getChildren()) {
                    //Map<String, Object> map = (Map<String, Object>) driverSnapshot.getValue();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        Map<String, Object> map = (Map<String, Object>) driverSnapshot.getValue();
                        if (map.get("name") != null) {
                            driverName = map.get("name").toString();
                        }
                        if (map.get("phoneNumber") != null) {
                            driverPhone = map.get("phoneNumber").toString();
                        }
                    }
                    float ratingSum = 0.0f;
                    float ratingTotal = 0.0f;
                    float ratingAvg = 0.0f;

                    DataSnapshot ratingSnapshot = driverSnapshot.child("rating");
                    if (ratingSnapshot.exists()) {
                        ratingSum = 0;
                        int ratingCount = 0;
                        for (DataSnapshot child : ratingSnapshot.getChildren()) {
                            if (child.getValue() instanceof Double) {
                                double ratingValue = (Double) child.getValue();
                                ratingSum += (float) ratingValue;
                                ratingCount++;
                            } else if (child.getValue() instanceof Long) {
                                long ratingValue = (Long) child.getValue();
                                ratingSum += (float) ratingValue;
                                ratingCount++;
                            } else if (child.getValue() instanceof String) {
                                try {
                                    float ratingValue = Float.parseFloat((String) child.getValue());
                                    ratingSum += ratingValue;
                                    ratingCount++;
                                } catch (NumberFormatException e) {
                                    // Handle the case where the value cannot be parsed as a float
                                }
                            } else {
                                // Handle the case where the value is not a Double, Long, or String
                            }
                        }
                        if (ratingCount > 0) {
                            ratingAvg = ratingSum / ratingCount;
                            Log.d("RatingAvg", String.valueOf(ratingAvg));
                        }
                    }



                    mRatingBar = ratingAvg;
                    Log.d("Rate",String.valueOf(mRatingBar));

                    SeeDriverObject object = new SeeDriverObject(driverName, driverPhone, mRatingBar);

                    list.add(object);

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