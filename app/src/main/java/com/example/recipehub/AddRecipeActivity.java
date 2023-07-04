package com.example.recipehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipehub.model.Recipe;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.RecipeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextIngredients;
    private EditText editTextInstructions;
    private Button buttonClear;
    private Button buttonCreate;

    private RecipeService recipeService;
    private String apiKey, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Get the API key and user ID from the intent
        Intent intent = getIntent();
        apiKey = intent.getStringExtra("api_key");
        userId = intent.getStringExtra("user_id");

        recipeService = ApiUtils.getRecipeService();

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        buttonClear = findViewById(R.id.buttonClear);
        buttonCreate = findViewById(R.id.buttonCreate);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipe();
            }
        });
    }

    private void clearFields() {
        editTextTitle.setText("");
        editTextDescription.setText("");
        editTextIngredients.setText("");
        editTextInstructions.setText("");
    }

    private void createRecipe() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String instructions = editTextInstructions.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Recipe recipe = new Recipe(Integer.parseInt(userId), title, description, ingredients, instructions);

        Call<Recipe> call = recipeService.createRecipe(apiKey, recipe);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddRecipeActivity.this, "Recipe created successfully!", Toast.LENGTH_SHORT).show();
                    navigateToPreHomeActivity2();
                } else {
                    Toast.makeText(AddRecipeActivity.this, "Failed to create recipe. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Toast.makeText(AddRecipeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToPreHomeActivity2() {
        Intent intent = new Intent(AddRecipeActivity.this, PreHomeActivity2.class);
        intent.putExtra("api_key", apiKey);
        intent.putExtra("user_id", userId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
