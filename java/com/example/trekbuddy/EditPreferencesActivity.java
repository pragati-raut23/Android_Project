package com.example.trekbuddy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.HashSet;
import java.util.Set;

public class EditPreferencesActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private LinearLayout preferencesContainer;
    private Set<String> userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_preferences);

        // Initialize Firebase references
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        preferencesContainer = findViewById(R.id.preferences_container);
        userPreferences = new HashSet<>();

        // Load user preferences and trekking locations
        loadTrekkingLocationsAndPreferences();
        //loadTrekkingLocations();

        // Save preferences on button click
        Button saveButton = findViewById(R.id.save_preferences_button);
        saveButton.setOnClickListener(v -> savePreferences());
    }

    private void loadTrekkingLocationsAndPreferences() {
        String currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(currentUserId).child("preferences")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Set<String> currentUserPreferences = new HashSet<>();
                        for (DataSnapshot prefSnapshot : snapshot.getChildren()) {
                            String locationId = prefSnapshot.getKey();
                            currentUserPreferences.add(locationId);
                        }
                        loadTrekkingLocations(currentUserPreferences);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPreferencesActivity.this, "Failed to load user preferences.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void loadTrekkingLocations(Set<String> userPreferences) {
//        mDatabase.child("trekkingLocations").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                preferencesContainer.removeAllViews();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String locationId = snapshot.getKey();
//                    String locationTitle = snapshot.child("title").getValue(String.class);
//
//                    if (locationTitle != null) {
//                        CheckBox checkBox = new CheckBox(EditPreferencesActivity.this);
//                        checkBox.setText(locationTitle);
//                        checkBox.setTag(locationId); // Store location ID in the tag for future reference
//
//                        // Set checked status and background color based on whether the location is in user preferences
//                        if (!userPreferences.isEmpty() && userPreferences.contains(locationId)) {
//                            checkBox.setChecked(true);
//                            checkBox.setBackgroundColor(getResources().getColor(R.color.selected_green));
//                        } else {
//                            checkBox.setBackgroundColor(getResources().getColor(R.color.default_gray));
//                        }
//
//                        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                            if (isChecked) {
//                                checkBox.setBackgroundColor(getResources().getColor(R.color.selected_green));
//                                addToPreferences(locationId);
//                            } else {
//                                checkBox.setBackgroundColor(getResources().getColor(R.color.default_gray));
//                                removeFromPreferences(locationId);
//                            }
//                        });
//
//                        preferencesContainer.addView(checkBox);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(EditPreferencesActivity.this, "Failed to load trekking locations.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void loadTrekkingLocations(Set<String> userPreferences) {
        mDatabase.child("trekkingLocations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                preferencesContainer.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String locationId = snapshot.getKey();
                    String locationTitle = snapshot.child("title").getValue(String.class);
                    String googleLink = snapshot.child("googleLink").getValue(String.class);

                    if (locationTitle != null && googleLink != null) {
                        // Create and configure the CheckBox for the location title
                        CheckBox checkBox = new CheckBox(EditPreferencesActivity.this);
                        checkBox.setText(locationTitle);
                        checkBox.setTag(locationId); // Store location ID in the tag for future reference

                        // Set checked status and background color based on user preferences
                        if (!userPreferences.isEmpty() && userPreferences.contains(locationId)) {
                            checkBox.setChecked(true);
                            checkBox.setBackgroundColor(getResources().getColor(R.color.selected_green));
                        } else {
                            checkBox.setBackgroundColor(getResources().getColor(R.color.default_gray));
                        }

                        // Add listener to update preferences in Firebase when checkbox is toggled
                        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                            if (isChecked) {
                                checkBox.setBackgroundColor(getResources().getColor(R.color.selected_green));
                                addToPreferences(locationId);
                            } else {
                                checkBox.setBackgroundColor(getResources().getColor(R.color.default_gray));
                                removeFromPreferences(locationId);
                            }
                        });

                        // Create and configure the TextView for the Google link
                        TextView googleLinkTextView = new TextView(EditPreferencesActivity.this);
                        googleLinkTextView.setText("Know more about it");
                        googleLinkTextView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                        googleLinkTextView.setPadding(10, 0, 10, 20);
                        googleLinkTextView.setTextSize(14);

                        // Set click listener to open Google Maps link in a browser
                        googleLinkTextView.setOnClickListener(v -> {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleLink));
                            startActivity(browserIntent);
                        });

                        // Add both the CheckBox and the Google link TextView to the container
                        preferencesContainer.addView(checkBox);
                        preferencesContainer.addView(googleLinkTextView);
                    } else {
                        Log.e("EditPreferencesActivity", "Location title or Google link is null in trekkingLocations");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditPreferencesActivity.this, "Failed to load trekking locations.", Toast.LENGTH_SHORT).show();
            }
        });
    }




    // Helper methods to update user preferences in Firebase
    private void addToPreferences(String locationId) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child("users").child(userId).child("preferences").child(locationId).setValue(true);
    }

    private void removeFromPreferences(String locationId) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child("users").child(userId).child("preferences").child(locationId).removeValue();
    }


    private void savePreferences() {
        String userId = mAuth.getCurrentUser().getUid();
        Set<String> preferences = new HashSet<>();
        Log.d("EditPreferencesActivity", "Saving preferences for userId: " + userId);

        // Collect selected preferences
        for (int i = 0; i < preferencesContainer.getChildCount(); i++) {
            View childView = preferencesContainer.getChildAt(i);
            if (childView instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) childView;
                if (checkBox.isChecked()) {
                    preferences.add(checkBox.getText().toString());
                    Log.d("EditPreferencesActivity", "Added to preferences: " + checkBox.getText().toString());
                }
            }
        }

        // Save preferences to Firebase
        mDatabase.child("users").child(userId).child("preferences").setValue(preferences)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("EditPreferencesActivity", "Preferences updated successfully");
                        Toast.makeText(this, "Preferences updated successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e("EditPreferencesActivity", "Failed to update preferences");
                        Toast.makeText(this, "Failed to update preferences", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
