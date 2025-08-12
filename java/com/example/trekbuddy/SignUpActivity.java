package com.example.trekbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, passwordEditText;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView loginlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        signUpButton = findViewById(R.id.sign_up_button);
        TextView loginlink = findViewById(R.id.login_link);

        signUpButton.setOnClickListener(v -> signUp());
        loginlink.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    private void signUp() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(SignUpActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();
                            writeNewUser(userId, name, email);
                        }
                        Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(userId, name, email, new ArrayList<>());

        mDatabase.child("users").child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "User data saved to database", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Failed to save user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
