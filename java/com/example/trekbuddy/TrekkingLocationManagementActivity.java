package com.example.trekbuddy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrekkingLocationManagementActivity extends AppCompatActivity {

    private EditText locationTitleEditText, googleLinkEditText;
    private Button saveLocationButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trekking_location_management);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference("trekkingLocations");

        // Initialize Views
        locationTitleEditText = findViewById(R.id.location_title);
        googleLinkEditText = findViewById(R.id.google_link);
        saveLocationButton = findViewById(R.id.save_location_button);

        // Set up the Save button click listener
        saveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrekkingLocation();
            }
        });
    }

    private void saveTrekkingLocation() {
        String locationTitle = locationTitleEditText.getText().toString().trim();
        String googleLink = googleLinkEditText.getText().toString().trim();

        if (TextUtils.isEmpty(locationTitle)) {
            Toast.makeText(this, "Please enter a location title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(googleLink)) {
            Toast.makeText(this, "Please enter a Google link", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a unique ID for each location entry
        String locationId = mDatabase.push().getKey();

        // Create a new TrekkingLocation object
        TrekkingLocation location = new TrekkingLocation(locationId, locationTitle, googleLink);

        // Save the location to Firebase Database
        if (locationId != null) {
            mDatabase.child(locationId).setValue(location).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Location added successfully", Toast.LENGTH_SHORT).show();
                    locationTitleEditText.setText("");
                    googleLinkEditText.setText("");
                } else {
                    Toast.makeText(this, "Failed to add location", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
