package com.example.trekbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText userIdEditText, passwordEditText;
    private Button loginButton;
    private TextView loginLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        userIdEditText = findViewById(R.id.user_id_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginButton = findViewById(R.id.login_button);
        loginLink = findViewById(R.id.login_link);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userIdEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Hardcoded admin credentials
                if (userId.equals("admin") && password.equals("admin123")) {
                    Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AdminLoginActivity.this, "Invalid admin credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginLink.setOnClickListener(v -> startActivity(new Intent(AdminLoginActivity.this, LoginActivity.class)));
    }
}
