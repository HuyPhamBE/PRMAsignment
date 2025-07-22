// AuthApi.java
package com.example.prmasignment.api;

import com.example.prmasignment.model.LoginRequest;
import com.example.prmasignment.model.LoginResponse;
import com.example.prmasignment.model.RegisterUserRequest;
import com.example.prmasignment.model.RegisterUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("/api/users")
    Call<RegisterUserResponse> register(@Body RegisterUserRequest request);
}
