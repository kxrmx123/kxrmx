package com.example.recipehub;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!isValidEmail(email)) {
            editTextEmail.setError("Invalid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        if (!isValidPassword(password)) {
            editTextPassword.setError("Invalid password");
            editTextPassword.requestFocus();
            return;
        }

        // Perform authentication check
        if (authenticate(email, password)) {
            // Authentication success
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            // Proceed to the next activity or perform desired actions
        } else {
            // Authentication failed
            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Password validation: At least 8 characters
        return password.length() >= 8;
    }

    private boolean authenticate(String email, String password) {
        // Perform authentication logic here
        // Replace with your own authentication mechanism (e.g., API call, database check, etc.)
        // Return true if the email and password are valid, false otherwise
        // For testing purposes, this example always returns true
        return true;
    }
}
