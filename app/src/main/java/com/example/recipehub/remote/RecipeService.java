package com.example.recipehub.remote;


import com.example.recipehub.model.Recipe;
import com.example.recipehub.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeService {


    @GET("api/recipes/")
    Call<List<Recipe>> searchRecipe(
            @Header("api-key") String apiKey,
            @Query("title[in]=") String title
    );

    @GET("api/recipes/")
    Call<List<Recipe>> searchRecipeBaseTitle(
            @Header("api-key") String apiKey,
            @Query("title[in]=") String title
    );

    @POST("api/recipes/")
    Call<Recipe> createRecipe(@Header("api-key") String apiKey, @Body Recipe recipe);

    @POST("api/recipes/{recipe_id}")
    Call<Recipe> updateRecipe(@Header("api-key") String apiKey, @Header("X-HTTP-Method-Override") String PUT, @Body Recipe updatedRecipe, @Path("recipe_id") String recipeId);


    @POST("api/recipes/{id}")
    Call<Void> deleteRecipe(@Header("api-key") String apiKey, @Header("X-HTTP-Method-Override") String DELETE, @Path("id") String recipeId);


}
