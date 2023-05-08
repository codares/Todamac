package com.avgator.todamac;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.avgator.todamac.Admin.AdminLogin;

public class MainActivity extends AppCompatActivity {

    private Button mDriver, mCustomer, mAdmin;
    private int numTaps = 0;
    private long lastTapTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDriver =  (Button) findViewById(R.id.driver);
        mCustomer =  (Button) findViewById(R.id.customer);
        mAdmin =  (Button) findViewById(R.id.admin);

        mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mCustomer .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastTapTime > 1000) {
                    // Reset the counter if there was a delay of more than 1 second between taps
                    numTaps = 1;
                } else {
                    numTaps++;
                }

                if (numTaps == 3) {
                    // Launch the AdminDashboard activity
                    Intent intent = new Intent(MainActivity.this, AdminLogin.class);
                    startActivity(intent);
                    finish();
                }

                lastTapTime = currentTime;
            }
        });

    }
}