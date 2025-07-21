package com.example.prmasignment.api;

import com.example.prmasignment.model.UpdateUserRequest;
import com.example.prmasignment.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminUserApi {
    String url_api = "/api/users";
    @GET(url_api)
    Call<List<UserResponse>> getUsers();
    @PUT(url_api + "/user")
    Call<UserResponse> updateUser(@Body UpdateUserRequest request);

    @PUT(url_api + "/{userId}")
    Call<UserResponse> deleteUser(@Path("userId") int userId);
}
