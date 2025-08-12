package com.example.trekbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // User is signed in, navigate to HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            // User is not signed in, navigate to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        // Finish the MainActivity to remove it from the back stack
        finish();

        ImageView adminIcon = findViewById(R.id.admin_icon);
        adminIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AdminLoginActivity
                Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

