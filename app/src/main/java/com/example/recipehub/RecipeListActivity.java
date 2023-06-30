package com.example.recipehub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipehub.adapter.RecipeAdapter;
import com.example.recipehub.model.Recipe;
import com.example.recipehub.model.User;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.RecipeService;
import com.example.recipehub.remote.UserService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener, RecipeAdapter.RecipeLongClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private RecipeService recipeService;
    private UserService userService;

    private String apiKey, userId;

    private int recipeId;
    private String searchTitle;

    private User user;

    public UserService getUserService() {
        return userService;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Intent intent = getIntent();

        // Use the recipeList as needed

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, this);
        recyclerView.setAdapter(recipeAdapter);

        recipeService = ApiUtils.getRecipeService();
        userService = ApiUtils.getUserService();

        // Get the API key and search title from the intent
        apiKey = intent.getStringExtra("api_key");
        userId = intent.getStringExtra("user_id");
        Log.d("Userid", userId);
        searchTitle = intent.getStringExtra("search_title");

        searchRecipes(apiKey, searchTitle);
        getUserRoleName(userId);
    }

    private void searchRecipes(String apiKey, String searchTitle) {
        Call<List<Recipe>> call = recipeService.searchRecipe(apiKey, searchTitle);
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    List<Recipe> recipes = response.body();
                    if (recipes != null && !recipes.isEmpty()) {
                        recipeAdapter.setRecipes(recipes);
                    } else {
                        Toast.makeText(RecipeListActivity.this, "No recipes found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RecipeListActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(RecipeListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getUserRoleName(String userId) {
        Call<User> call = userService.getUser(apiKey, userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    if (user != null) {
                        if (user.getPermission().equalsIgnoreCase("user")) {

                            //Toast.makeText(RecipeListActivity.this, "You're a regular user", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            //Toast.makeText(RecipeListActivity.this, "You're an admin", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RecipeListActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RecipeListActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RecipeListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        // Handle the recipe click event
        // You can redirect the user to another activity here, passing any necessary data
        String recipeTitle = recipe.getTitle();

        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra("api_key", apiKey);

        // Serialize the Recipe object using Gson
        Gson gson = new Gson();
        String recipeJson = gson.toJson(recipe);
        Recipe recipe2 = gson.fromJson(recipeJson, Recipe.class);
        intent.putExtra("recipe_json", recipeJson);

        intent.putExtra("user_id", userId);

        startActivity(intent);
    }

    private void showOptionsDialog( Recipe recipe) {
        if (user.getPermission().equalsIgnoreCase("user")) {

        }

        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(R.array.recipe_options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            updateRecipe(recipe);
                            break;
                        case 1:
                            deleteRecipe(recipe);
                            break;
                    }
                }
            });
            builder.create().show();
        }


    }

    private void updateRecipe(Recipe recipe) {
        Intent intent = new Intent(this, UpdateRecipeActivity.class);
        intent.putExtra("api_key", apiKey);
        intent.putExtra("user_id", userId);

        // Serialize the Recipe object using Gson
        Gson gson = new Gson();
        String recipeJson = gson.toJson(recipe);
        intent.putExtra("recipe_json", recipeJson);

        startActivity(intent);
    }


    private void deleteRecipe(final Recipe recipe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> call = recipeService.deleteRecipe(apiKey, String.valueOf(recipe.getRecipe_id()));
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(RecipeListActivity.this, "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
                                    // Refresh the recipe list
                                    searchRecipes(apiKey, searchTitle);
                                } else {
                                    Toast.makeText(RecipeListActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(RecipeListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onRecipeLongClick(Recipe recipe) {
        showOptionsDialog(recipe);
    }
}




