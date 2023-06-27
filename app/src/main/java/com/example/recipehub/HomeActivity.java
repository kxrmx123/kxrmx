package com.example.recipehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipehub.model.Recipe;
import com.example.recipehub.model.User;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.RecipeService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch;
    Button btnLogout;

    RecipeService recipeService;
    String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get the API key from the previous activity
        Intent intent = getIntent();
        apiKey = intent.getStringExtra("api_key");

        // Initialize the RecipeService
        recipeService = ApiUtils.getRecipeService();

        // Get references to the form elements
        edtSearch = findViewById(R.id.editTextSearch);
        btnSearch = findViewById(R.id.buttonSearch);
        btnLogout = findViewById(R.id.buttonLogout);

        // Set onClick action to btnSearch
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the search query entered by the user
                String searchQuery = edtSearch.getText().toString().trim();

                // Validate the search query
                if (searchQuery.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
                } else {
                    // Pass the API key and search query to RecipeListActivity
                    Intent intent = new Intent(HomeActivity.this, RecipeListActivity.class);
                    intent.putExtra("api_key", apiKey);
                    intent.putExtra("search_title", searchQuery);
                    startActivity(intent);
                }
            }
        });

        // Set onClick action to btnLogout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear all data and restart the app
                clearDataAndRestartApp();
            }
        });
    }

    private void clearDataAndRestartApp() {
        // Clear all data and restart the app from the beginning
        // Add any additional data clearing code specific to your app, such as clearing preferences, databases, etc.

        // Example: Clearing preferences
        // SharedPreferences preferences = getSharedPreferences("YourPreferencesName", MODE_PRIVATE);
        // SharedPreferences.Editor editor = preferences.edit();
        // editor.clear();
        // editor.apply();

        // Restart the app from the beginning
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}



