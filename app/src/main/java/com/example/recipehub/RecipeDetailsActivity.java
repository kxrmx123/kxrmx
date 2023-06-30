package com.example.recipehub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipehub.adapter.ReviewAdapter;
import com.example.recipehub.model.Recipe;
import com.example.recipehub.model.Review;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.RecipeService;
import com.example.recipehub.remote.ReviewService;
import com.example.recipehub.remote.UserService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    private TextView textViewAverageRating;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewIngredients;
    private TextView textViewInstructions;
    private Button buttonCreateReview;
    private RecyclerView recyclerViewReviews;

    private RecipeService recipeService;
    private ReviewService reviewService;
    private UserService userService;
    private String apiKey;
    private Recipe recipe;

    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Initialize views
        textViewAverageRating = findViewById(R.id.textViewAverageRating);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewIngredients = findViewById(R.id.textViewIngredients);
        textViewInstructions = findViewById(R.id.textViewInstructions);
        buttonCreateReview = findViewById(R.id.buttonCreateReview);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);

        // Get the API key from the intent
        Intent intent = getIntent();
        apiKey = intent.getStringExtra("api_key");

        // Initialize the recipeService and reviewService using ApiUtils
        recipeService = ApiUtils.getRecipeService();
        reviewService = ApiUtils.getReviewService();

        // Deserialize the Recipe object from JSON using Gson
        Gson gson = new Gson();
        String recipeJson = intent.getStringExtra("recipe_json");
        recipe = gson.fromJson(recipeJson, Recipe.class);

        // Display the recipe attributes
        displayRecipeAttributes(recipe);

        // Load and display the reviews for the recipe
        loadReviewsForRecipe(apiKey, recipe.getRecipe_id());

        // Set onClick action to the create review button
        buttonCreateReview.setOnClickListener(view -> navigateToCreateReviewPage());
    }


    private void displayRecipeAttributes(Recipe recipe) {
        textViewTitle.setText(recipe.getTitle());
        textViewDescription.setText(recipe.getDescription());
        //textViewTitle.setText(Integer.toString(recipe.getRecipeId()));
        //textViewDescription.setText(Integer.toString(recipe.getUserId()));
        textViewIngredients.setText(recipe.getIngredients());
        textViewInstructions.setText(recipe.getInstructions());
    }



    private void loadReviewsForRecipe(String apiKey, int recipeId) {
        reviewService.getReviewsByRecipeId(apiKey, String.valueOf(recipeId)).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    List<Review> reviews = response.body();
                    if (reviews != null && !reviews.isEmpty()) {
                        displayReviews(reviews);
                        calculateAverageRating(reviews);
                    } else {
                        Toast.makeText(RecipeDetailsActivity.this, "No reviews available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RecipeDetailsActivity.this, "Failed to load reviews", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Toast.makeText(RecipeDetailsActivity.this, "Error loading reviews: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayReviews(List<Review> reviews) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewReviews.setLayoutManager(layoutManager);
        ReviewAdapter reviewAdapter = new ReviewAdapter(reviews, userService, apiKey);
        recyclerViewReviews.setAdapter(reviewAdapter);
    }

    private void calculateAverageRating(List<Review> reviews) {
        if (reviews != null) {
            double totalRating = 0;
            int numReviews = reviews.size();
            for (Review review : reviews) {
                totalRating += review.getRating();
            }
            if (numReviews > 0) {
                double averageRating = totalRating / numReviews;
                textViewAverageRating.setText(String.format("Average Rating: %.2f", averageRating));
            } else {
                textViewAverageRating.setText("Average Rating: N/A");
            }
        } else {
            textViewAverageRating.setText("Average Rating: N/A");
        }
    }


    private void navigateToCreateReviewPage() {
        SharedPreferences sharedPreferences = getSharedPreferences("your_preference_name", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", "");

        Intent intent = new Intent(RecipeDetailsActivity.this, CreateReviewActivity.class);
        intent.putExtra("api_key", apiKey);
        intent.putExtra("recipeId", recipe.getRecipe_id());
        intent.putExtra("recipeTitle", recipe.getTitle());
        intent.putExtra("userId", userId);
        Toast.makeText(this, "Recipeid: " + recipe.getRecipe_id(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "userid: " + userId, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
