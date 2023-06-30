package com.example.recipehub;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        boolean isUsernameValid = isUsernameValid(username);
        boolean isEmailValid = isEmailValid(email);
        boolean isPasswordValid = isPasswordValid(password);

        if (!isUsernameValid || !isEmailValid || !isPasswordValid) {
            StringBuilder errorMessage = new StringBuilder();

            if (!isUsernameValid) {
                errorMessage.append("Username should have at least 8 alphanumeric characters.\n");
            }

            if (!isEmailValid) {
                errorMessage.append("Invalid email address.\n");
            }

            if (!isPasswordValid) {
                errorMessage.append("Password should have at least 8 characters.\n");
            }

            showWarningDialog(errorMessage.toString().trim());
            return;
        }

        // Perform signup process
        // ...

        // Clear input fields
        editTextUsername.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");

        // Show success message or navigate to the next activity
        // ...
    }

    private boolean isUsernameValid(String username) {
        // Username validation: At least 8 alphanumeric characters
        return username.matches("[a-zA-Z0-9]{8,}");
    }

    private boolean isEmailValid(String email) {
        // Email validation using built-in Patterns class
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        // Password validation: At least 8 characters
        return password.length() >= 8;
    }

    private void showWarningDialog(String message) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_warning);
        TextView textViewMessage = dialog.findViewById(R.id.textViewMessage);
        Button buttonClose = dialog.findViewById(R.id.buttonClose);

        textViewMessage.setText(message);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
