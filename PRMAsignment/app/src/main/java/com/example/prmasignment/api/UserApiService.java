package com.example.prmasignment.api;

import com.example.prmasignment.dtos.request.UpdateUserByUsernameRequest;
import com.example.prmasignment.dtos.request.UpdateUserRequest;
import com.example.prmasignment.dtos.response.UserResponse;
import com.example.prmasignment.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiService {
    @GET("api/users/{userId}")
    Call<UserResponse> getUserById(@Path("userId") String userId);

    @PUT("api/users/user")
    Call<UserResponse> updateUser(@Body UpdateUserRequest request);

    @GET("api/users/{username}/username")
    Call<UserResponse> getUserByUsername(@Path("username") String username);

    @PUT("api/users/{username}/name")
    Call<UserResponse> updateUserByUsername(@Path("username") String username, @Body UpdateUserByUsernameRequest request);

}
