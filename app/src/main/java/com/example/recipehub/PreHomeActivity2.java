package com.example.recipehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipehub.model.User;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreHomeActivity2 extends AppCompatActivity {

    private TextView textViewWelcome;
    private Button buttonAddRecipe, buttonUpdateDeleteRecipe;
    private UserService userService;

    private String apiKey, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_home2);

        Intent intent = getIntent();
        apiKey = intent.getStringExtra("api_key");
        userId = intent.getStringExtra("user_id");

        textViewWelcome = findViewById(R.id.textViewWelcome);
        buttonAddRecipe = findViewById(R.id.buttonAddRecipe);
        buttonUpdateDeleteRecipe = findViewById(R.id.buttonUpdateDeleteRecipe);

        userService = ApiUtils.getUserService();

        getUsername(userId);

        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRecipeIntent = new Intent(PreHomeActivity2.this, AddRecipeActivity.class);
                addRecipeIntent.putExtra("api_key", apiKey);
                addRecipeIntent.putExtra("user_id", userId);
                startActivity(addRecipeIntent);
            }
        });

        buttonUpdateDeleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateDeleteRecipeIntent = new Intent(PreHomeActivity2.this, HomeActivity.class);
                updateDeleteRecipeIntent.putExtra("api_key", apiKey);
                updateDeleteRecipeIntent.putExtra("user_id", userId);
                startActivity(updateDeleteRecipeIntent);
            }
        });
    }

    private void getUsername(String userId) {
        Call<User> call = userService.getUser(apiKey, userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        String username = user.getUsername();
                        textViewWelcome.setText("Welcome back, " + username + "!");
                    } else {
                        Toast.makeText(PreHomeActivity2.this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PreHomeActivity2.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(PreHomeActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
