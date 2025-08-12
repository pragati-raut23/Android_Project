package com.example.trekbuddy;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;  // Unique identifier for the user
    private String name;     // User's name
    private String email;    // User's email
    private List<String> preferences; // List of user preferences
    private String status;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    // Constructor for creating a new user
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.preferences = new ArrayList<>();
    }

    public User(String userId, String name, String email, List<String> preferences) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.preferences = preferences != null ? preferences : new ArrayList<>(); // Initialize preferences
    }


    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setStatus(String status) {
        this.status=status;
    }
}
