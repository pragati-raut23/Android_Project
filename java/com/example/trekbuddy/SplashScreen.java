package com.example.trekbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    // Splash screen duration (e.g., 3 seconds)
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the splash screen layout
        setContentView(R.layout.activity_splash_screen);

        // Delayed transition to the next activity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is logged in using Firebase authentication
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent;
                if (currentUser != null) {
                    // If user is signed in, navigate to HomeActivity
                    intent = new Intent(SplashScreen.this, HomeActivity.class);
                } else {
                    // If user is not signed in, navigate to LoginActivity
                    intent = new Intent(SplashScreen.this, LoginActivity.class);
                }
                startActivity(intent);
                finish(); // Close the SplashScreen activity
            }
        }, SPLASH_TIME_OUT); // Wait for SPLASH_TIME_OUT duration
    }
}