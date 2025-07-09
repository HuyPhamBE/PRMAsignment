package com.example.prmasignment.ui.adminBrand;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.repository.AdminBrandRepository;

public class AdminBrandViewModelFactory implements ViewModelProvider.Factory {
    private final AdminBrandRepository repository;

    public AdminBrandViewModelFactory(AdminBrandRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AdminBrandViewModel.class)) {
            return (T) new AdminBrandViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
