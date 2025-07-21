package com.example.prmasignment.ui.adminUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.repository.AdminUserRepository;

public class AdminUserViewModelFactory implements ViewModelProvider.Factory {

    private final AdminUserRepository repository;

    public AdminUserViewModelFactory(AdminUserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AdminUserViewModel.class)) {
            return (T) new AdminUserViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
