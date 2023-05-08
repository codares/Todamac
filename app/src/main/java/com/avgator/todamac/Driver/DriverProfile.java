package com.avgator.todamac.Driver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.avgator.todamac.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;

public class DriverProfile extends AppCompatActivity {

    private TextView fullNameTextView, phoneTextView, emailTextView;
    private ImageView mProfileImage;
    private FirebaseAuth mAuth;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        mAuth = FirebaseAuth.getInstance();

        // Initialize the views
        fullNameTextView = findViewById(R.id.fullname_text_view);
        phoneTextView = findViewById(R.id.phone_text_view);
        emailTextView = findViewById(R.id.email_text_view);

        mProfileImage = findViewById(R.id.profile_picture);

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a file picker to select an image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        String driverId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driverId);
        mDriverDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("name") != null) {
                        fullNameTextView.setText(map.get("name").toString());
                    }else if(map.get("Name") != null) {
                        fullNameTextView.setText(map.get("Name").toString());
                    }
                    if (map.get("phone") != null) {
                        emailTextView.setText(map.get("phone").toString());
                    }
                    if (map.get("email") != null) {
                        phoneTextView.setText(map.get("email").toString());
                    }
                    if (map.get("profile_picture") != null) {
                        // Load the user's profile picture from Firebase Storage
                        String imageUrl = map.get("profile_picture").toString();
                        Glide.with(DriverProfile.this)
                                .load(imageUrl)
                                .into(mProfileImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of the selected image
            Uri imageUri = data.getData();

            // Upload the image to Firebase Storage
            String driverId = mAuth.getCurrentUser().getUid();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("profile_images").child(driverId);
            storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the download URL of the uploaded image
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            // Update the user's profile picture in the Firebase Realtime Database
                            DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(driverId);
                            customerRef.child("profile_picture").setValue(downloadUrl.toString());
                            Toast.makeText(DriverProfile.this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DriverProfile.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}