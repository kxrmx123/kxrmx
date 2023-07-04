package com.example.recipehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipehub.model.Recipe;
import com.example.recipehub.model.User;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.RecipeService;
import com.example.recipehub.remote.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRecipeActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextIngredients;
    private EditText editTextInstructions;
    private Button buttonClear;
    private Button buttonUpdate;

    private RecipeService recipeService;
    private UserService userService;
    private String apiKey;
    private String recipe_id;
    private String user_id, username;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        Intent intent = getIntent();
        apiKey = intent.getStringExtra("api_key");
        recipe_id = intent.getStringExtra("recipe_id");
        user_id = intent.getStringExtra("user_id");

        recipeService = ApiUtils.getRecipeService();
        userService = ApiUtils.getUserService();

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        buttonClear = findViewById(R.id.buttonClear);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        Gson gson = new Gson();
        String recipeJson = intent.getStringExtra("recipe_json");
        recipe = gson.fromJson(recipeJson, Recipe.class);

        getUser();

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecipe();
            }
        });
    }

    private void getUser() {
        Call<User> call = userService.getUser(apiKey, user_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        username = user.getUsername();
                        // Set the obtained username to the field
                        // This assumes there is a TextView to display the username with id textViewUsername
                        // Example: textViewUsername.setText(username);
                    } else {
                        Toast.makeText(UpdateRecipeActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateRecipeActivity.this, "Failed to get user. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UpdateRecipeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void clearFields() {
        editTextTitle.setText("");
        editTextDescription.setText("");
        editTextIngredients.setText("");
        editTextInstructions.setText("");
    }

    private void updateRecipe() {
        if (!editTextTitle.getText().toString().trim().isEmpty()) {
            recipe.setTitle(editTextTitle.getText().toString().trim());
        }

        if (!editTextDescription.getText().toString().trim().isEmpty()) {
            recipe.setDescription(editTextDescription.getText().toString().trim());
        }

        if (!editTextIngredients.getText().toString().trim().isEmpty()) {
            recipe.setIngredients(editTextIngredients.getText().toString().trim());
        }

        if (!editTextInstructions.getText().toString().trim().isEmpty()) {
            recipe.setInstructions(editTextInstructions.getText().toString().trim());
        }

        // You may want to perform additional validation here before updating the recipe

        Recipe recipe2 = new Recipe(Integer.parseInt(user_id), recipe.getTitle(), recipe.getDescription(), recipe.getIngredients(), recipe.getInstructions());
        recipe2.setRecipe_id(Integer.parseInt(recipe_id));
        Call<Recipe> call = recipeService.updateRecipe(apiKey, "PUT", recipe2, recipe_id);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateRecipeActivity.this, "Recipe updated successfully!", Toast.LENGTH_SHORT).show();
                    navigateToPreHomeActivity();
                } else {
                    Toast.makeText(UpdateRecipeActivity.this, "Failed to update recipe. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Toast.makeText(UpdateRecipeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToPreHomeActivity() {
        Intent intent = new Intent(UpdateRecipeActivity.this, PreHomeActivity2.class);
        intent.putExtra("api_key", apiKey);
        intent.putExtra("user_id", user_id);
        intent.putExtra("username", username); // Pass the username obtained from the getUser() method
        startActivity(intent);
        finish(); // Finish this activity so that when you go back from PreHomeActivity2, this won't be shown.
    }
}
