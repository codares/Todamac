package com.avgator.todamac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.avgator.todamac.Customer.CustomerProfile;
import com.avgator.todamac.Customer.History;
import com.avgator.todamac.Customer.ReportDriver;
import com.avgator.todamac.Customer.SeeDrivers;

public class UserDashboard extends AppCompatActivity {

    private LinearLayout seeDrivers, reportDriver, bookRide, history, seeProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // Find the menu items by their IDs
        seeDrivers = findViewById(R.id.see_drivers);
        seeProfile = findViewById(R.id.see_profile);
        reportDriver = findViewById(R.id.report_driver);
        bookRide = findViewById(R.id.book_ride);
        history = findViewById(R.id.history);

        // Set the onClickListener for each menu item
        seeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, CustomerProfile.class);
                startActivity(intent);
                return;
            }
        });

        seeDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, SeeDrivers.class);
                startActivity(intent);
                return;
            }
        });

        reportDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, ReportDriver.class);
                startActivity(intent);
            }
        });

        bookRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, CustomerMapActivity.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, History.class);
                intent.putExtra("customerOrDriver", "Customers");
                startActivity(intent);
            }
        });
    }
}