package com.example.trekbuddy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SendNotificationActivity extends AppCompatActivity {

    private EditText notificationMessageEditText;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        notificationMessageEditText = findViewById(R.id.notification_message);
        Button sendNotificationButton = findViewById(R.id.send_notification);

        mDatabase = FirebaseDatabase.getInstance().getReference("notifications");

        sendNotificationButton.setOnClickListener(v -> {
            String message = notificationMessageEditText.getText().toString();
            if (!message.isEmpty()) {
                sendNotification(message);
            }
        });
    }

    private void sendNotification(String message) {
        // Initialize Firebase Database reference for the "notifications" table
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("notifications");

        // Generate a unique notification ID
        String notificationId = mDatabase.push().getKey();
        if (notificationId != null) {
            // Create a map to store notification details
            Map<String, Object> notificationData = new HashMap<>();
            notificationData.put("message", message);
            notificationData.put("timestamp", System.currentTimeMillis());

            // Save notification data to the "notifications" table in Firebase
            mDatabase.child(notificationId).setValue(notificationData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Notification Sent and Stored Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to send notification", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Error generating notification ID", Toast.LENGTH_SHORT).show();
        }
    }

}
