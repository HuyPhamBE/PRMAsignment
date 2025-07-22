package com.example.prmasignment.ui.auth;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.model.LoginRequest;
import com.example.prmasignment.model.LoginResponse;
import com.example.prmasignment.repository.AuthRepository;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<LoginResponse> loginResult = new MutableLiveData<>();
    private AuthRepository repository = new AuthRepository();

    public void login(String username, String password) {
        LoginRequest request = new LoginRequest(username, password);
        repository.login(request, loginResult);
    }
}

