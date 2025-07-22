package com.example.prmasignment.ui.auth;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.model.RegisterUserRequest;
import com.example.prmasignment.model.RegisterUserResponse;
import com.example.prmasignment.repository.AuthRepository;

public class RegisterViewModel extends ViewModel {
    public MutableLiveData<RegisterUserResponse> registerResult = new MutableLiveData<>();
    private AuthRepository repository = new AuthRepository();

    public void register(RegisterUserRequest request) {
        repository.register(request, registerResult);
    }
}

