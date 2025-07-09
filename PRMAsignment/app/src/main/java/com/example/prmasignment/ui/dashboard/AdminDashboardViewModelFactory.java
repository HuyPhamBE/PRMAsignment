package com.example.prmasignment.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AdminDashboardViewModelFactory implements ViewModelProvider.Factory {

    private final String token;

    public AdminDashboardViewModelFactory(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AdminDashboardViewModel.class)) {
            return (T) new AdminDashboardViewModel(token);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
