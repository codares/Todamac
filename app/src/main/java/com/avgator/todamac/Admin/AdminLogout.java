package com.avgator.todamac.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.avgator.todamac.MainActivity;
import com.avgator.todamac.R;

public class AdminLogout extends AppCompatActivity {


        private Button mLogoutButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_logout);

            // Get references to UI elements
            mLogoutButton = findViewById(R.id.logout_button);

            // Set a click listener for the logout button
            mLogoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Clear the login state and return to the login activity
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("isLoggedIn");
                    editor.apply();
                    Intent intent = new Intent(AdminLogout.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }