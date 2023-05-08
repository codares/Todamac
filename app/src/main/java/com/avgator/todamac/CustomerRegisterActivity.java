package com.avgator.todamac;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerRegisterActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;

    private EditText fullname, phone, email, password, conpassword;
    private Button registerBtn;
    private TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

            mAuth = FirebaseAuth.getInstance();

            fullname = findViewById(R.id.fullName);
            phone = findViewById(R.id.phone);
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);

            registerBtn = findViewById(R.id.registerBtn);
            loginBtn = findViewById(R.id.loginBtn);

            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
                }
            });

    }

    private void registerUser() {

        String fullnameTxt = fullname.getText().toString().trim();
        String phoneTxt = phone.getText().toString().trim();
        String emailTxt = email.getText().toString().trim();
        String passwordTxt = password.getText().toString().trim();

        if(fullnameTxt.isEmpty()){
            fullname.setError("Full name  is required!");
            fullname.requestFocus();
            return;
        }

        if(phoneTxt.isEmpty()){
            phone.setError("Phone is required!");
            phone.requestFocus();
            return;
        }

        if(emailTxt.isEmpty()){
            email.setError("Email  is required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }

        if(passwordTxt.isEmpty()){
            password.setError("Password  is required!");
            password.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener( CustomerRegisterActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullnameTxt, phoneTxt, emailTxt);
                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child("Customers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(CustomerRegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(CustomerRegisterActivity.this, "Registration failed please try again..", Toast.LENGTH_LONG).show();

                                            }

                                        }
                                    });
                        }else{
                            Toast.makeText(CustomerRegisterActivity.this, "Registration failed please try again later..", Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }


}