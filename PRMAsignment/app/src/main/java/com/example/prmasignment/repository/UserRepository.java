package com.example.prmasignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.api.UserApiService;
import com.example.prmasignment.dtos.request.UpdateUserByUsernameRequest;
import com.example.prmasignment.dtos.request.UpdateUserRequest;
import com.example.prmasignment.dtos.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final UserApiService apiService;

    public UserRepository(String token) {
        this.apiService = ApiClient.getClientWithAuth(token).create(UserApiService.class);
    }

    public void getUserById(String userId, MutableLiveData<UserResponse> liveData) {
        apiService.getUserById(userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void getUserByUsername(String username, MutableLiveData<UserResponse> liveData) {
        Call<UserResponse> call = apiService.getUserByUsername(username);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
    }


    public void updateUser(String username, UpdateUserByUsernameRequest request, MutableLiveData<Boolean> resultLiveData) {
        apiService.updateUserByUsername(username, request).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                resultLiveData.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                resultLiveData.postValue(false);
            }
        });
    }
}
