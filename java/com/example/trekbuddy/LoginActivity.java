package com.example.trekbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private TextView forgotPasswordLink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        signInButton = findViewById(R.id.sign_in_button);
        TextView signUpLink = findViewById(R.id.sign_up_link);
        forgotPasswordLink = findViewById(R.id.forgot_password_link);

        signInButton.setOnClickListener(v -> signIn());
        signUpLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
        forgotPasswordLink.setOnClickListener(v -> showForgotPasswordDialog());
        ImageView adminIcon = findViewById(R.id.admin_icon);
        adminIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AdminLoginActivity
                Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Email and password cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sign In Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showForgotPasswordDialog() {
        EditText resetEmail = new EditText(this);
        resetEmail.setHint("Enter your email");

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Reset Password")
                .setMessage("Enter your email to receive a password reset link")
                .setView(resetEmail)
                .setPositiveButton("Send", (dialog, which) -> {
                    String email = resetEmail.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(LoginActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        resetPassword(email);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
