package com.example.trekbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button manageTrekkingAreasBtn, sendNotificationsBtn, viewUsageMetricsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        manageTrekkingAreasBtn = findViewById(R.id.manage_trekking_areas);
        sendNotificationsBtn = findViewById(R.id.send_notifications);
        viewUsageMetricsBtn = findViewById(R.id.view_usage_metrics);

        manageTrekkingAreasBtn.setOnClickListener(v -> {
            // Navigate to Trekking Area Management
            startActivity(new Intent(this, TrekkingLocationManagementActivity.class));
        });

        sendNotificationsBtn.setOnClickListener(v -> {
            // Navigate to Notifications Activity
            startActivity(new Intent(this, SendNotificationActivity.class));
        });

        viewUsageMetricsBtn.setOnClickListener(v -> {
            // Navigate to Usage Metrics Activity
            startActivity(new Intent(this, UsageMetricsActivity.class));
        });
    }
}
