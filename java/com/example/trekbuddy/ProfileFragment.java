package com.example.trekbuddy;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

//public class ProfileActivity extends AppCompatActivity {
//    private static final int PICK_IMAGE_REQUEST = 1;
//
//    private EditText userNameEditText;
//    private TextView userPreferencesTextView;
//    private ImageView profilePicture;
//    private Button changePictureButton, removePictureButton, editPreferencesButton;
//    private DatabaseReference mDatabase;
//    private FirebaseAuth mAuth;
//    private StorageReference mStorageRef;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//
//        profilePicture = findViewById(R.id.profile_picture);
//        changePictureButton = findViewById(R.id.change_picture_button);
//        removePictureButton = findViewById(R.id.remove_picture_button);
//        userNameEditText = findViewById(R.id.user_name);
//        userPreferencesTextView = findViewById(R.id.user_preferences);
//        editPreferencesButton = findViewById(R.id.edit_preferences_button);
//        Button saveButton = findViewById(R.id.save_button);
//        Button logoutButton = findViewById(R.id.logout_button);
//
//        loadUserProfile();
//
//        changePictureButton.setOnClickListener(v -> openFileChooser());
//        removePictureButton.setOnClickListener(v -> removeProfilePicture());
//        editPreferencesButton.setOnClickListener(v -> openEditPreferences());
//        saveButton.setOnClickListener(v -> saveUserProfile());
//        logoutButton.setOnClickListener(v -> {
//            mAuth.signOut();
//            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//            finish();
//        });
//    }
//
//    private void loadUserProfile() {
//        String userId = mAuth.getCurrentUser().getUid();
//        // Load Profile Picture
//        mDatabase.child("users").child(userId).child("profilePictureUrl").get().addOnSuccessListener(dataSnapshot -> {
//            String profilePictureUrl = dataSnapshot.getValue(String.class);
//            if (profilePictureUrl != null && !profilePictureUrl.isEmpty()) {
//                Glide.with(this).load(profilePictureUrl).circleCrop().into(profilePicture); // Load from Firebase Storage
//            } else {
//                profilePicture.setImageResource(R.drawable.default_avatar); // Show default avatar
//            }
//        });
//
//        // Load other profile details
//        mDatabase.child("users").child(userId).get().addOnSuccessListener(dataSnapshot -> {
//            String name = dataSnapshot.child("name").getValue(String.class);
//            List<String> preferences = new ArrayList<>();
//            for (DataSnapshot prefSnapshot : dataSnapshot.child("preferences").getChildren()) {
//                preferences.add(prefSnapshot.getValue(String.class));
//            }
//            userNameEditText.setText(name);
//            userPreferencesTextView.setText(String.join(", ", preferences));
//        });
//    }
//
//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri imageUri = data.getData();
//            uploadProfilePicture(imageUri);
//        }
//    }
//
//    private void uploadProfilePicture(Uri imageUri) {
//        String userId = mAuth.getCurrentUser().getUid();
//        StorageReference fileReference = mStorageRef.child("profile_pictures/" + userId + ".jpg");
////StorageReference fileReference = mStorageRef.child("profile_pictures/" + userId + ".jpg");
//        fileReference.putFile(imageUri)
//                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                    String imageUrl = uri.toString();
//                    mDatabase.child("users").child(userId).child("profilePictureUrl").setValue(imageUrl);
//                    Glide.with(this).load(imageUrl).into(profilePicture);
//                    Toast.makeText(ProfileActivity.this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
//                }))
//                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Failed to upload picture", Toast.LENGTH_SHORT).show());
//    }
//
//    private void removeProfilePicture() {
//        String userId = mAuth.getCurrentUser().getUid();
//        StorageReference fileReference = mStorageRef.child("profile_pictures/" + userId + ".jpg");
//
//        fileReference.delete()
//                .addOnSuccessListener(aVoid -> {
//                    mDatabase.child("users").child(userId).child("profilePictureUrl").removeValue();
//                    profilePicture.setImageResource(R.drawable.default_avatar);
//                    Toast.makeText(ProfileActivity.this, "Profile picture removed", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Failed to remove picture", Toast.LENGTH_SHORT).show());
//    }
//
//    private void saveUserProfile() {
//        String userId = mAuth.getCurrentUser().getUid();
//        String updatedName = userNameEditText.getText().toString().trim();
//        mDatabase.child("users").child(userId).child("name").setValue(updatedName)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void openEditPreferences() {
//        Intent intent = new Intent(ProfileActivity.this, EditPreferencesActivity.class); // Create this activity for editing preferences
//        startActivity(intent);
//    }
//}



//public class ProfileActivity extends AppCompatActivity {
//    private static final int PICK_IMAGE_REQUEST = 1;
//
//    private EditText userNameEditText;
//    private TextView userPreferencesTextView;
//    private ImageView profilePicture;
//    private Button changePictureButton, removePictureButton, editPreferencesButton;
//    private DatabaseReference mDatabase;
//    private FirebaseAuth mAuth;
//    private StorageReference mStorageRef;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//
//        profilePicture = findViewById(R.id.profile_picture);
//        changePictureButton = findViewById(R.id.change_picture_button);
//        removePictureButton = findViewById(R.id.remove_picture_button);
//        userNameEditText = findViewById(R.id.user_name);
//        userPreferencesTextView = findViewById(R.id.user_preferences);
//        editPreferencesButton = findViewById(R.id.edit_preferences_button);
//        Button saveButton = findViewById(R.id.save_button);
//        Button logoutButton = findViewById(R.id.logout_button);
//
//        // Ensure the user is authenticated
//        if (mAuth.getCurrentUser() != null) {
//            loadUserProfile();
//        } else {
//            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//            finish();
//        }
//
//        changePictureButton.setOnClickListener(v -> openFileChooser());
//        removePictureButton.setOnClickListener(v -> removeProfilePicture());
//        editPreferencesButton.setOnClickListener(v -> openEditPreferences());
//        saveButton.setOnClickListener(v -> saveUserProfile());
//        logoutButton.setOnClickListener(v -> {
//            mAuth.signOut();
//            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
//            finish();
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadUserProfile(); // Refresh profile details when returning to this activity
//    }
//
//    private void loadUserProfile() {
//        String userId = mAuth.getCurrentUser().getUid();
//        // Load Profile Picture
//        mDatabase.child("users").child(userId).child("profilePictureUrl").get().addOnSuccessListener(dataSnapshot -> {
//            String profilePictureUrl = dataSnapshot.getValue(String.class);
//            if (!TextUtils.isEmpty(profilePictureUrl)) {
//                Glide.with(this).load(profilePictureUrl).circleCrop().into(profilePicture);
//            } else {
//                profilePicture.setImageResource(R.drawable.default_avatar);
//            }
//        });
//
//        // Load other profile details
//        mDatabase.child("users").child(userId).get().addOnSuccessListener(dataSnapshot -> {
//            String name = dataSnapshot.child("name").getValue(String.class);
//            List<String> preferenceTitles = new ArrayList<>();
//            List<String> preferenceIds = new ArrayList<>();
//
//            // Collect all preference IDs
//            for (DataSnapshot prefSnapshot : dataSnapshot.child("preferences").getChildren()) {
//                String locationId = prefSnapshot.getKey();
//                if (locationId != null) {
//                    preferenceIds.add(locationId);
//                }
//            }
//
//            // Fetch titles for each preference ID from trekkingLocations
//            for (String locationId : preferenceIds) {
//                mDatabase.child("trekkingLocations").child(locationId).child("title").get().addOnSuccessListener(titleSnapshot -> {
//                    String title = titleSnapshot.getValue(String.class);
//                    if (title != null) {
//                        preferenceTitles.add(title);
//                    }
//
//                    // Once all titles are retrieved, update the TextView
//                    if (preferenceTitles.size() == preferenceIds.size()) {
//                        userPreferencesTextView.setText(TextUtils.join(", ", preferenceTitles));
//                    }
//                });
//            }
//
//            userNameEditText.setText(name);
//        });
//
//    }
//
//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri imageUri = data.getData();
//            uploadProfilePicture(imageUri);
//        }
//    }
//
//    private void uploadProfilePicture(Uri imageUri) {
//        String userId = mAuth.getCurrentUser().getUid();
//        StorageReference fileReference = mStorageRef.child("profile_pictures/" + userId + ".jpg");
//        fileReference.putFile(imageUri)
//                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                    String imageUrl = uri.toString();
//                    mDatabase.child("users").child(userId).child("profilePictureUrl").setValue(imageUrl);
//                    Glide.with(this).load(imageUrl).circleCrop().into(profilePicture);
//                    Toast.makeText(ProfileActivity.this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
//                }))
//                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Failed to upload picture", Toast.LENGTH_SHORT).show());
//    }
//
//    private void removeProfilePicture() {
//        String userId = mAuth.getCurrentUser().getUid();
//        StorageReference fileReference = mStorageRef.child("profile_pictures/" + userId + ".jpg");
//
//        fileReference.delete()
//                .addOnSuccessListener(aVoid -> {
//                    mDatabase.child("users").child(userId).child("profilePictureUrl").removeValue();
//                    profilePicture.setImageResource(R.drawable.default_avatar);
//                    Toast.makeText(ProfileActivity.this, "Profile picture removed", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Failed to remove picture", Toast.LENGTH_SHORT).show());
//    }
//
//    private void saveUserProfile() {
//        String userId = mAuth.getCurrentUser().getUid();
//        String updatedName = userNameEditText.getText().toString().trim();
//        mDatabase.child("users").child(userId).child("name").setValue(updatedName)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void openEditPreferences() {
//        Intent intent = new Intent(ProfileActivity.this, EditPreferencesActivity.class);
//        startActivity(intent);
//    }
//}


public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText userNameEditText;
    private TextView userPreferencesTextView;
    private ImageView profilePicture;
    private Button changePictureButton, removePictureButton, editPreferencesButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        profilePicture = view.findViewById(R.id.profile_picture);
        changePictureButton = view.findViewById(R.id.change_picture_button);
        removePictureButton = view.findViewById(R.id.remove_picture_button);
        userNameEditText = view.findViewById(R.id.user_name);
        userPreferencesTextView = view.findViewById(R.id.user_preferences);
        editPreferencesButton = view.findViewById(R.id.edit_preferences_button);
        Button saveButton = view.findViewById(R.id.save_button);
        Button logoutButton = view.findViewById(R.id.logout_button);

        // Ensure the user is authenticated
        if (mAuth.getCurrentUser() != null) {
            loadUserProfile();
        } else {
            Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            requireActivity().startActivity(new Intent(getContext(), MainActivity.class));
            requireActivity().finish();
        }

        changePictureButton.setOnClickListener(v -> openFileChooser());
        removePictureButton.setOnClickListener(v -> removeProfilePicture());
        editPreferencesButton.setOnClickListener(v -> openEditPreferences());
        saveButton.setOnClickListener(v -> saveUserProfile());
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            requireActivity().startActivity(new Intent(getContext(), LoginActivity.class));
            requireActivity().finish();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserProfile(); // Refresh profile details when returning to this fragment
    }

    private void loadUserProfile() {
        String userId = mAuth.getCurrentUser().getUid();

        mDatabase.child("users").child(userId).child("profilePictureUrl").get().addOnSuccessListener(dataSnapshot -> {
            String profilePictureUrl = dataSnapshot.getValue(String.class);
            if (!TextUtils.isEmpty(profilePictureUrl)) {
                Glide.with(this).load(profilePictureUrl).circleCrop().into(profilePicture);
            } else {
                profilePicture.setImageResource(R.drawable.default_avatar);
            }
        });

        mDatabase.child("users").child(userId).get().addOnSuccessListener(dataSnapshot -> {
            String name = dataSnapshot.child("name").getValue(String.class);
            List<String> preferenceTitles = new ArrayList<>();
            List<String> preferenceIds = new ArrayList<>();

            for (DataSnapshot prefSnapshot : dataSnapshot.child("preferences").getChildren()) {
                String locationId = prefSnapshot.getKey();
                if (locationId != null) {
                    preferenceIds.add(locationId);
                }
            }

            for (String locationId : preferenceIds) {
                mDatabase.child("trekkingLocations").child(locationId).child("title").get().addOnSuccessListener(titleSnapshot -> {
                    String title = titleSnapshot.getValue(String.class);
                    if (title != null) {
                        preferenceTitles.add(title);
                    }

                    if (preferenceTitles.size() == preferenceIds.size()) {
                        userPreferencesTextView.setText(TextUtils.join(", ", preferenceTitles));
                    }
                });
            }

            userNameEditText.setText(name);
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            uploadProfilePicture(imageUri);
        }
    }

    private void uploadProfilePicture(Uri imageUri) {
        String userId = mAuth.getCurrentUser().getUid();
        StorageReference fileReference = mStorageRef.child("profile_pictures/" + userId + ".jpg");
        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    mDatabase.child("users").child(userId).child("profilePictureUrl").setValue(imageUrl);
                    Glide.with(this).load(imageUrl).circleCrop().into(profilePicture);
                    Toast.makeText(getContext(), "Profile picture updated!", Toast.LENGTH_SHORT).show();
                }))
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to upload picture", Toast.LENGTH_SHORT).show());
    }

    private void removeProfilePicture() {
        String userId = mAuth.getCurrentUser().getUid();
        StorageReference fileReference = mStorageRef.child("profile_pictures/" + userId + ".jpg");

        fileReference.delete()
                .addOnSuccessListener(aVoid -> {
                    mDatabase.child("users").child(userId).child("profilePictureUrl").removeValue();
                    profilePicture.setImageResource(R.drawable.default_avatar);
                    Toast.makeText(getContext(), "Profile picture removed", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to remove picture", Toast.LENGTH_SHORT).show());
    }

    private void saveUserProfile() {
        String userId = mAuth.getCurrentUser().getUid();
        String updatedName = userNameEditText.getText().toString().trim();
        mDatabase.child("users").child(userId).child("name").setValue(updatedName)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openEditPreferences() {
        Intent intent = new Intent(getContext(), EditPreferencesActivity.class);
        startActivity(intent);
    }
}

