package com.example.trekbuddy;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
//import com.mikepenz.iconics.IconicsDrawable;
//import com.mikepenz.ionicons_typeface_library.Ionicons;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.*;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        // Set initial selection to Profile
        bottomNavigationView.setSelectedItemId(R.id.nav_chat);
    }

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_friends) {
            Log.d(TAG, "Navigating to FriendSuggestionFragment");
            selectedFragment = new FriendSuggestionFragment();
        } else if (itemId == R.id.nav_profile) {
            Log.d(TAG, "Navigating to ProfileActivity");
            selectedFragment = new ProfileFragment();
        } else if (itemId == R.id.nav_chat) {
            Log.d(TAG, "Navigating to ChatActivity");
            selectedFragment = new ChatListFragment();
        } else if (itemId == R.id.nav_notifications) {
            Log.d(TAG, "Navigating to Notifications");
            selectedFragment = new FriendRequestFragment();
        } else {
            Log.d(TAG, "Unknown item selected");
            return false;
        }

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.home_content);

        if (currentFragment == null || !currentFragment.getClass().equals(selectedFragment.getClass())) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_content, selectedFragment);
            transaction.addToBackStack(null); // Optional: add to backstack
            transaction.commit();
        }

        return true;
    }
}


