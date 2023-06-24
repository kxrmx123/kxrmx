package com.example.recipehub.remote;

public class ApiUtils {

    // REST API server URL
    public static final String BASE_URL = "https://unresenting-yards.000webhostapp.com/prestige/";

    // return UserService instance
    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

}
