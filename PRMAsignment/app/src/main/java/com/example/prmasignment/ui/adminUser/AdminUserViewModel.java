package com.example.prmasignment.ui.adminUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.model.UpdateUserRequest;
import com.example.prmasignment.model.UserResponse;
import com.example.prmasignment.repository.AdminUserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUserViewModel extends ViewModel {
    private final AdminUserRepository repository;

    public MutableLiveData<List<UserResponse>> usersLiveData = new MutableLiveData<>();
    public MutableLiveData<UserResponse> deleteUserResult = new MutableLiveData<>();

    public AdminUserViewModel(AdminUserRepository repository) {
        this.repository = repository;
    }

    public void fetchUsers() {
        repository.getUsers().enqueue(new Callback<>() {
            @Override public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                usersLiveData.setValue(response.body());
            }

            @Override public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                usersLiveData.setValue(null);
            }
        });
    }



    public void deleteUser(int userId) {
        repository.deleteUser(userId).enqueue(new Callback<>() {
            @Override public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                deleteUserResult.setValue(response.body());
            }

            @Override public void onFailure(Call<UserResponse> call, Throwable t) {
                deleteUserResult.setValue(null);
            }
        });
    }
}

