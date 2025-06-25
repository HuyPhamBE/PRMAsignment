
package com.example.prmasignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.api.AuthApi;
import com.example.prmasignment.model.LoginRequest;
import com.example.prmasignment.model.LoginResponse;
import com.example.prmasignment.model.RegisterUserRequest;
import com.example.prmasignment.model.RegisterUserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private AuthApi authApi;

    public AuthRepository() {
        // Không cần token
        authApi = ApiClient.getClientNoAuth().create(AuthApi.class);
    }

    public void login(LoginRequest request, MutableLiveData<LoginResponse> liveData) {
        authApi.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void register(RegisterUserRequest request, MutableLiveData<RegisterUserResponse> liveData) {
        authApi.register(request).enqueue(new Callback<RegisterUserResponse>() {
            @Override
            public void onResponse(Call<RegisterUserResponse> call, Response<RegisterUserResponse> response) {
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<RegisterUserResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }
}
