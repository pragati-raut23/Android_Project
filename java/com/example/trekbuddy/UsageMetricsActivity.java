package com.example.trekbuddy;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsageMetricsActivity extends AppCompatActivity {

    private TextView usageMetricsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_metrics);

        usageMetricsTextView = findViewById(R.id.usage_metrics_text_view);
        displayUsageMetrics();
    }

    private void displayUsageMetrics() {
        DatabaseReference metricsRef = FirebaseDatabase.getInstance().getReference("UsageMetrics");

        metricsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder metrics = new StringBuilder();
                for (DataSnapshot data : snapshot.getChildren()) {
                    metrics.append(data.getKey()).append(": ").append(data.getValue()).append("\n");
                }
                usageMetricsTextView.setText(metrics.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UsageMetricsActivity.this, "Failed to load metrics", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
