package com.example.recipehub.remote;

import com.example.recipehub.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReviewService {

    @GET("api/reviews/{recipeId}")
    Call<List<Review>> getReviewsByRecipeId(@Path("recipeId") String recipeId);

}

