package com.example.recipehub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipehub.R;
import com.example.recipehub.adapter.RecipeAdapter;
import com.example.recipehub.model.Recipe;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.RecipeService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private RecipeService recipeService;

    private String apiKey;
    private String searchTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Intent intent = getIntent();
        String recipeListJson = intent.getStringExtra("recipeListJson");

        // Convert the JSON string back to List<Recipe>
        Gson gson = new Gson();
        List<Recipe> recipeList = gson.fromJson(recipeListJson, new TypeToken<List<Recipe>>() {}.getType());

        // Use the recipeList as needed

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);

        recipeService = ApiUtils.getRecipeService();

        // Get the API key and search title from the intent
        apiKey = getIntent().getStringExtra("api_key");
        searchTitle = getIntent().getStringExtra("search_title");

        searchRecipes(apiKey, searchTitle);
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

    @Override
    public void onRecipeClick(Recipe recipe) {
        // Handle the recipe click event
        // You can redirect the user to another activity here, passing any necessary data
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra("api_key", apiKey);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}
