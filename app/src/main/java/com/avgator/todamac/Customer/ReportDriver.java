package com.avgator.todamac.Customer;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.avgator.todamac.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ReportDriver extends AppCompatActivity {

        private Button mSubmit, mCancel, mUploadMediaBtn;
        private EditText mDriverName, mDate, mPlateNumber;
        private RadioGroup radioGroup;
        private String mediaUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_driver);

        //hooks for buton
        mSubmit = findViewById(R.id.submit_btn);
        mCancel = findViewById(R.id.cancel_btn);
        mUploadMediaBtn = findViewById(R.id.upload_media_btn);

        //hooks for textview
        mDriverName = findViewById(R.id.driver_name_input);
        mDate = findViewById(R.id.date_input);
        mPlateNumber = findViewById(R.id.plate_number_input);
        radioGroup = findViewById(R.id.radio_group);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the selected radio button id
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

                // get a reference to the selected radio button view
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                //get reporter id
                String reporterId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String driverName = mDriverName.getText().toString();
                String date = mDate.getText().toString();
                String plateNumber = mPlateNumber.getText().toString();

                // get the text of the selected radio button
                String selectedRadioText = selectedRadioButton.getText().toString();
                if (selectedRadioText.equals("Other")) {
                    EditText otherReasonEditText = findViewById(R.id.otherReasonEditText);
                    String otherReasonText = otherReasonEditText.getText().toString();
                    selectedRadioText = "Other : " + otherReasonText;
                }

                Report newReport = new Report(reporterId, driverName, date, plateNumber, selectedRadioText);

                // Get a reference to the "ReportList" child node in the database
                DatabaseReference reportRef = FirebaseDatabase.getInstance().getReference().child("Report");

                // Generate a new unique key for the report
                String reportKey = reportRef.push().getKey();


                // Show a progress dialog
                ProgressDialog progressDialog = new ProgressDialog(ReportDriver.this);
                progressDialog.setMessage("Uploading Media...");
                progressDialog.show();

                if (mediaUri != null) {
                    StorageReference mediaRef = FirebaseStorage.getInstance().getReference().child("media").child(reportKey);
                    String finalSelectedRadioText = selectedRadioText;
                    String fileExtension = getFileExtension(mediaUri);

                    if (fileExtension.equals("mp4") || fileExtension.equals("mov") || fileExtension.equals("avi")) {

                        mediaRef.putFile(mediaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get the download URL of the uploaded media
                                mediaRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        mediaUrl = uri.toString();

                                        mediaUrl = mediaUrl + ".mp4";

                                        // Create a new report object with the media download URL
                                        Report newReport = new Report(reporterId, driverName, date, plateNumber, finalSelectedRadioText, mediaUrl);

                                        // Get a reference to the "ReportList" child node in the database
                                        DatabaseReference reportRef = FirebaseDatabase.getInstance().getReference().child("Report");

                                        // Save the report data to the database using the unique key
                                        reportRef.child(reportKey).setValue(newReport);

                                        progressDialog.dismiss();
                                        // Show a success message
                                        Toast.makeText(ReportDriver.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();

                                        // Clear the form
                                        clearForm();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Show an error message
                                Toast.makeText(ReportDriver.this, "Failed to upload media." + e.getStackTrace(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {

                        mediaRef.putFile(mediaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get the download URL of the uploaded media
                                mediaRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        mediaUrl = uri.toString();

                                        // Create a new report object with the media download URL
                                        Report newReport = new Report(reporterId, driverName, date, plateNumber, finalSelectedRadioText, mediaUrl);

                                        // Get a reference to the "ReportList" child node in the database
                                        DatabaseReference reportRef = FirebaseDatabase.getInstance().getReference().child("Report");

                                        // Save the report data to the database using the unique key
                                        reportRef.child(reportKey).setValue(newReport);

                                        progressDialog.dismiss();
                                        // Show a success message
                                        Toast.makeText(ReportDriver.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();

                                        // Clear the form
                                        clearForm();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Show an error message
                                Toast.makeText(ReportDriver.this, "Failed to upload media." + e.getStackTrace(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } else {
                    // Create a new report object without the media download URL
                    Report newReport2 = new Report(reporterId, driverName, date, plateNumber, selectedRadioText);

                    // Get a reference to the "ReportList" child node in the database
                    DatabaseReference reportRef2 = FirebaseDatabase.getInstance().getReference().child("Report");

                    // Save the report data to the database using the unique key
                    reportRef2.child(reportKey).setValue(newReport2);

                    // Show a success message
                    Toast.makeText(ReportDriver.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Clear the form
                    clearForm();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = findViewById(R.id.radio_group);
                radioGroup.clearCheck();
                EditText otherEditText = findViewById(R.id.otherReasonEditText);
                mDriverName.setText("");
                mDate.setText("");
                mPlateNumber.setText("");
                otherEditText.setText("");
            }
        });

        mUploadMediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/* video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Media"), 1);
            }
        });

    }

    public void clearForm(){

        RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.clearCheck();
        EditText otherEditText = findViewById(R.id.otherReasonEditText);
        mDriverName.setText("");
        mDate.setText("");
        mPlateNumber.setText("");
        otherEditText.setText("");

    }

    private Uri mediaUri;

    //method to handle the selected image or video
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            mediaUri = data.getData();
        }
    }

    // Helper function to get the file extension from a Uri
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}