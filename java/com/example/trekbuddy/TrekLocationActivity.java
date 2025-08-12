package com.example.trekbuddy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class TrekLocationActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trek_location);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        LinearLayout checkBoxContainer = findViewById(R.id.checkBoxContainer);
        Button savePreferencesButton = findViewById(R.id.savePreferencesButton);

        savePreferencesButton.setOnClickListener(v -> {
            List<String> selectedPreferences = new ArrayList<>();
            for (int i = 0; i < checkBoxContainer.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) checkBoxContainer.getChildAt(i);
                if (checkBox.isChecked()) {
                    selectedPreferences.add(checkBox.getText().toString());
                }
            }

            if (currentUser != null) {
                mDatabase.child("users").child(currentUser.getUid()).child("preferences").setValue(selectedPreferences)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Preferences saved successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed to save preferences.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
