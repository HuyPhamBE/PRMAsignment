package com.example.prmasignment.api;

import com.example.prmasignment.model.UpdateUserRequest;
import com.example.prmasignment.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminUserApi {
    @PUT("/user")
    Call<UserResponse> updateUser(@Body UpdateUserRequest request);

    @PUT("/{userId}")
    Call<UserResponse> deleteUser(@Path("userId") int userId);
}
