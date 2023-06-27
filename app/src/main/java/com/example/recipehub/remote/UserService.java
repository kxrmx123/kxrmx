package com.example.recipehub.remote;

import com.example.recipehub.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @FormUrlEncoded
    @POST("api/users/login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/users/login")
    Call<User> loginEmail(@Field("email") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/users/login")
    Call<User> gettoken(@Header("api-key") String apiKey);

    @POST("api/users")
    Call<User> addUser(@Header("api-key") String apiKey, @Body User user);

    @GET("api/users/{userId}")
    Call<User> getUser(@Path("userId") String userId);

}
