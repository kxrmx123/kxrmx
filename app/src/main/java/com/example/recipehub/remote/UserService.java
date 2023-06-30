package com.example.recipehub.remote;

import com.example.recipehub.model.User;

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

    @GET("api/users/{userId}")
    Call<User> getUser(@Header("api-key") String apiKey, @Path("userId") String userId);

    @GET("api/users/")
    Call<List<User>> getUserByName(@Header("api-key") String apiKey, @Query("username[in]=") String username );

}
