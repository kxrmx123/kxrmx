package com.example.recipehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipehub.model.ErrorResponse;
import com.example.recipehub.model.User;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.UserService;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername; //email or username is allowed
    EditText edtPassword;
    Button btnLogin;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get references to form elements
        edtUsername = findViewById(R.id.editTextEmail); //email or username is allowed
        edtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.login_button);

        // get UserService instance
        userService = ApiUtils.getUserService();

        // set onClick action to btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get username and password entered by user

                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();



                // validate form, make sure it is not empty
                if (validateLogin(username, password)) {
                    // do login
                    doLogin(username, password);
                }
            }
        });
    }

    /**
     * Validate value of username and password entered. Client side validation.
     * @param username
     * @param password
     * @return
     */
    private boolean validateLogin(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            displayToast("Username or email is required");
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            displayToast("Password is required");
            return false;
        }
        return true;
    }

    /**
     * Call REST API to login
     * @param username
     * @param password
     */
    private void doLogin(String username, String password) {
        Call<User> call;
        if (username.contains("@")) {
            call = userService.loginEmail(username, password);
        } else {
            call = userService.login(username, password);
        }
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                // received reply from REST API
                if (response.isSuccessful()) {
                    // parse response to POJO
                    User user = response.body();

                    if (user != null && user.getToken() != null) {
                        // successful login. server replies a token value


                        // Check the role_permissions
                        if (user.getPermission().equalsIgnoreCase("user")) {
                            // Redirect to Page 1 (user page)
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("api_key", user.getToken());
                            intent.putExtra("user_id", String.valueOf(user.getId()));
                            intent.putExtra("username", user.getUsername());



                            startActivity(intent);
                            finish();
                        } else {
                            // Redirect to Page 2 (admin page)
                            Intent intent = new Intent(LoginActivity.this, PreHomeActivity2.class);
                            intent.putExtra("api_key", user.getToken());
                            intent.putExtra("user_id", String.valueOf(user.getId()));
                            intent.putExtra("username", user.getUsername());


                            startActivity(intent);
                            finish();
                        }
                    } else {
                        displayToast("Login not successful");
                    }
                } else if (response.errorBody() != null) {
                    // parse response to POJO
                    String errorResp = null;
                    try {
                        errorResp = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ErrorResponse e = new Gson().fromJson(errorResp, ErrorResponse.class);
                    displayToast(e.getError().getMessage());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                displayToast("Error connecting to server.");
                displayToast(t.getMessage());
            }

        });
    }

    /**
     * Display a Toast message
     * @param message
     */
    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}