package com.avgator.todamac.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avgator.todamac.Customer.Report;
import com.avgator.todamac.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ArrayList<Report> list;
    private ReportListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        // Get references to UI elements
        recyclerView = findViewById(R.id.list_view_report);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a reference to the "drivers" node in the Firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference("Report");

        // Initialize driver list and adapter
        list = new ArrayList<>();
        mAdapter = new ReportListAdapter(this, list);
        recyclerView.setAdapter(mAdapter);


        // Retrieve all drivers and their information
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot reportSnapshot : dataSnapshot.getChildren()) {
                    //Map<String, Object> map = (Map<String, Object>) driverSnapshot.getValue();

                    /* String reportId = driverSnapshot.child("reportId").getValue(String.class);
                    String driverName = driverSnapshot.child("driverName").getValue(String.class);
                    String reportDate = driverSnapshot.child("reportDate").getValue(String.class);
                    String reportPlateNumber = driverSnapshot.child("reportPlateNumber").getValue(String.class);
                    String reportType = driverSnapshot.child("reportType").getValue(String.class);
                    */

                    Report report = reportSnapshot.getValue(Report.class);

                    list.add(report);

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