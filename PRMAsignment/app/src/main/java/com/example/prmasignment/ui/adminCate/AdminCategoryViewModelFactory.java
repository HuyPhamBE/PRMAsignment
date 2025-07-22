package com.example.prmasignment.ui.adminCate;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AdminCategoryViewModelFactory implements ViewModelProvider.Factory {
    private final String token;

    public AdminCategoryViewModelFactory(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AdminCategoryViewModel.class)) {
            return (T) new AdminCategoryViewModel(token);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
