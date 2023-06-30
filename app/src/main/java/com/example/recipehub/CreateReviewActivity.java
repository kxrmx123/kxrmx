package com.example.recipehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipehub.R;
import com.example.recipehub.model.Review;
import com.example.recipehub.remote.ApiUtils;
import com.example.recipehub.remote.ReviewService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReviewActivity extends AppCompatActivity {

    private TextView textViewHeader;
    private EditText editTextComment;
    private Spinner spinnerRating;
    private Button buttonClear;
    private Button buttonCreate;

    private ReviewService reviewService;
    private String apiKey;
    private String recipeId;
    private String recipeTitle;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        Intent intent = getIntent();
        recipeTitle = intent.getStringExtra("recipeTitle");
        apiKey = intent.getStringExtra("api_key");
        recipeId = (intent.getStringExtra("recipeId"));
        userId = (intent.getStringExtra("userId"));



        reviewService = ApiUtils.getReviewService();

        textViewHeader = findViewById(R.id.textViewHeader);
        editTextComment = findViewById(R.id.editTextComment);
        spinnerRating = findViewById(R.id.spinnerRating);
        buttonClear = findViewById(R.id.buttonClear);
        buttonCreate = findViewById(R.id.buttonCreate);

        textViewHeader.setText("Write a review for this recipe");

        ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.rating_values,
                android.R.layout.simple_spinner_item
        );
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(ratingAdapter);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createReview();
            }
        });
    }

    private void clearFields() {
        editTextComment.setText("");
        spinnerRating.setSelection(0);
    }

    private void createReview() {
        String comment = editTextComment.getText().toString().trim();
        int rating = Integer.parseInt(spinnerRating.getSelectedItem().toString());

        // Perform any necessary validation of the input fields

        Review review = new Review( Integer.parseInt(recipeId), Integer.parseInt(userId), rating, comment);

        Call<Review> call = reviewService.createReview(apiKey, review);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateReviewActivity.this, "Review created successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                    navigateToHomeActivity();
                } else {
                    Toast.makeText(CreateReviewActivity.this, "Failed to create review. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Toast.makeText(CreateReviewActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void navigateToHomeActivity() {
        Intent intent = new Intent(CreateReviewActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
