package com.example.recipehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipehub.model.User;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.RecipeService;
import com.example.recipehub.remote.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    EditText edtSearch;
    Button btnSearch;
    Button btnLogout;

    RecipeService recipeService;
    UserService userService;
    String apiKey, userId, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get the API key from the previous activity
        Intent intent = getIntent();
        apiKey = intent.getStringExtra("api_key");
        userId = intent.getStringExtra("user_id");
        username = intent.getStringExtra("username");




        // Initialize the RecipeService and UserService
        recipeService = ApiUtils.getRecipeService();
        userService = ApiUtils.getUserService();



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
                    // Check the role of the user
                    checkUserRole(searchQuery);
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





    private void checkUserRole(final String searchQuery) {
        Call<List<User>> call = userService.getUserByName(apiKey, username); // Using getUserByName instead of getUser
        call.enqueue(new Callback<List<User>>() { // Using List<User> as the response type
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body(); // The response will be a list of users
                    if (users != null && !users.isEmpty()) {
                        User user = users.get(0); // Assuming the response contains only one user with the given userId
                        Log.d("userer", user.toString());

                        String roleName = user.getRole();
                        if (roleName.equalsIgnoreCase("user")) {
                            // If the role is user, go to RecipeListActivity
                            goToRecipeListActivity(searchQuery);
                        } else {
                            // If the role is not user, go to RecipeListActivity2
                            goToRecipeListActivity2(searchQuery);
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void goToRecipeListActivity(String searchQuery) {
        Intent intent = new Intent(HomeActivity.this, RecipeListActivity.class);
        intent.putExtra("api_key", apiKey);
        intent.putExtra("user_id", userId);
        intent.putExtra("search_title", searchQuery);
        startActivity(intent);
    }

    private void goToRecipeListActivity2(String searchQuery) {
        Intent intent = new Intent(HomeActivity.this, RecipeListActivity.class);
        intent.putExtra("api_key", apiKey);
        intent.putExtra("user_id", userId);
        intent.putExtra("search_title", searchQuery);
        startActivity(intent);
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




