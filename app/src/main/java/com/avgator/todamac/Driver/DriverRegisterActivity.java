package com.avgator.todamac.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.avgator.todamac.DriverLoginActivity;
import com.avgator.todamac.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DriverRegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText fullname, license, phone, address, plateNumber, email, password;
    private Button registerBtn;
    private TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        mAuth = FirebaseAuth.getInstance();

        fullname = findViewById(R.id.fullName);
        license = findViewById(R.id.licenseId);
        address = findViewById(R.id.address);
        plateNumber = findViewById(R.id.plateNumber);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverRegisterActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDriver();
            }
        });

    }

    private void registerDriver() {

        String fullnameTxt = fullname.getText().toString().trim();
        String licenseTxt = license.getText().toString().trim();
        String phoneTxt = phone.getText().toString().trim();
        String addressTxt = address.getText().toString().trim();
        String plateNumberTxt = plateNumber.getText().toString().trim();
        String emailTxt = email.getText().toString().trim();
        String passwordTxt = password.getText().toString().trim();

        if (fullnameTxt.isEmpty()) {
            fullname.setError("Full name  is required!");
            fullname.requestFocus();
            return;
        }

        if (phoneTxt.isEmpty()) {
            phone.setError("Phone is required!");
            phone.requestFocus();
            return;
        }

        if (emailTxt.isEmpty()) {
            email.setError("Email  is required!");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }

        if (passwordTxt.isEmpty()) {
            password.setError("Password  is required!");
            password.requestFocus();
            return;
        }

        if (addressTxt.isEmpty()) {
            address.setError("Address is required!");
            address.requestFocus();
            return;
        }

        if (plateNumberTxt.isEmpty()) {
            plateNumber.setError("Plate number is required!");
            plateNumber.requestFocus();
            return;
        }

        if (licenseTxt.isEmpty()) {
            license.setError("License ID is required!");
            license.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(DriverRegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Driver driver = new Driver(fullnameTxt, licenseTxt, phoneTxt, addressTxt, plateNumberTxt, emailTxt);
                    FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                fullname.setText("");
                                license.setText("");
                                phone.setText("");
                                address.setText("");
                                plateNumber.setText("");
                                email.setText("");
                                password.setText("");
                                Toast.makeText(DriverRegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(DriverRegisterActivity.this, "Registration failed please try again..", Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                } else {
                    Toast.makeText(DriverRegisterActivity.this, "Registration failed please try again later..", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}