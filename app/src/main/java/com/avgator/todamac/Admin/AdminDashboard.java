package com.avgator.todamac.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.avgator.todamac.Driver.DriverList;
import com.avgator.todamac.R;

public class AdminDashboard extends AppCompatActivity {

    private LinearLayout seeDriversMenu, authorityReportMenu, supportMenu, settingsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        seeDriversMenu = findViewById(R.id.menu_see_drivers);
        authorityReportMenu = findViewById(R.id.menu_authority_report);
        supportMenu = findViewById(R.id.menu_support);
        settingsMenu = findViewById(R.id.menu_settings);

        // Set the onClickListener for each menu item
        seeDriversMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, DriverList.class);
                startActivity(intent);
                return;
            }
        });

        authorityReportMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, ReportList.class);
                startActivity(intent);
                return;
            }
        });

        supportMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to be executed when the Support menu item is clicked
            }
        });

        settingsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, AdminLogout.class);
                startActivity(intent);
                return;
            }
        });
    }
}