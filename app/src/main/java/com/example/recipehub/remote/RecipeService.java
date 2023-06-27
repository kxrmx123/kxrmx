package com.example.recipehub.remote;


import com.example.recipehub.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RecipeService {


    @GET("api/recipes/")
    Call<List<Recipe>> searchRecipe(
            @Header("api-key") String apiKey,
            @Query("title[in]=") String title
    );

}
