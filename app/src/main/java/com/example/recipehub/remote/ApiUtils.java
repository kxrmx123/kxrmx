package com.example.recipehub.remote;

import com.example.recipehub.model.Recipe;
import com.example.recipehub.model.Review;

public class ApiUtils {

    // REST API server URL
    public static final String BASE_URL = "https://unresenting-yards.000webhostapp.com/prestige/";

    // return UserService instance
    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static RecipeService getRecipeService() {
        return RetrofitClient.getClient(BASE_URL).create(RecipeService.class);
    }

    public static ReviewService getReviewService() {
        return RetrofitClient.getClient(BASE_URL).create(ReviewService.class);
    }

}
