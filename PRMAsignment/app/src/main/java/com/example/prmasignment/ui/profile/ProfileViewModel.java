package com.example.prmasignment.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.dtos.request.UpdateUserByUsernameRequest;
import com.example.prmasignment.dtos.response.UserResponse;
import com.example.prmasignment.dtos.request.UpdateUserRequest;
import com.example.prmasignment.repository.UserRepository;

public class ProfileViewModel extends ViewModel {

    private final UserRepository repository;

    public MutableLiveData<UserResponse> userLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> updateResultLiveData = new MutableLiveData<>();

    public ProfileViewModel(String token) {
        this.repository = new UserRepository(token);
    }

    public void getUserByUsername(String username) {
        repository.getUserByUsername(username, userLiveData);
    }

    public void fetchUserProfile(String userId) {
        repository.getUserById(userId, userLiveData);
    }

    public void fetchUserProfileByUsername(String username) {
        repository.getUserByUsername(username, userLiveData);
    }


    public void updateUserProfile(String username, UpdateUserByUsernameRequest request) {
        repository.updateUser(username, request, updateResultLiveData);
    }
}
