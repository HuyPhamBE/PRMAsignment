package com.example.prmasignment.repository;

import com.example.prmasignment.api.AdminProductApi;
import com.example.prmasignment.api.AdminUserApi;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.model.UpdateUserRequest;
import com.example.prmasignment.model.UserResponse;

import java.util.List;

import retrofit2.Call;

public class AdminUserRepository {
        private final AdminUserApi api;

        public AdminUserRepository(String token) {
            this.api = ApiClient.getClientWithAuth(token).create(AdminUserApi.class);
        }

        public Call<List<UserResponse>> getUsers() {
            return api.getUsers();
        }

        public Call<UserResponse> updateUser(UpdateUserRequest request) {
            return api.updateUser(request);
        }

        public Call<UserResponse> deleteUser(int userId) {
            return api.deleteUser(userId);
        }


}
