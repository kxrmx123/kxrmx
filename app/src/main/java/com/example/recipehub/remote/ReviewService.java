package com.example.recipehub.remote;

import com.example.recipehub.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewService {

    @GET("api/reviews/")
    Call<List<Review>> getReviewsByRecipeId(@Header("api-key") String apiKey, @Query("recipe_id[in]=") String recipeId );

    @POST("api/reviews")
    Call<Review> createReview(@Header("api-key") String apiKey, @Body Review review);

    @FormUrlEncoded
    @POST("api/reviews/")
    Call<Void> deleteReviewsfromRecipe(@Header("api-key") String apiKey, @Header("X-HTTP-Method-Override") String DELETE, @Query("recipe_id[in]=") String recipeId, @Field("review_id") String reviewId);
}

