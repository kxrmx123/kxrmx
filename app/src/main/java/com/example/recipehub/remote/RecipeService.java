package com.example.recipehub.remote;


import com.example.recipehub.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
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



    @DELETE("api/recipes/{id}")
    Call<Void> deleteRecipe(@Header("api-key") String apiKey, @Path("id") String recipeId);


}
