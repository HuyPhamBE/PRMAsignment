package com.example.prmasignment.ui.adminProduct;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.repository.AdminProductRepository;

public class AdminProductViewModelFactory implements ViewModelProvider.Factory {
    private final AdminProductRepository repository;

    public AdminProductViewModelFactory(AdminProductRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AdminProductViewModel.class)) {
            return (T) new AdminProductViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

