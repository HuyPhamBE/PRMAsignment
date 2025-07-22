package com.example.prmasignment.ui.adminStoreLoction;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.repository.AdminStoreLocationRepository;

public class AdminStoreLocationViewModelFactory implements ViewModelProvider.Factory {
    private final AdminStoreLocationRepository repository;

    public AdminStoreLocationViewModelFactory(AdminStoreLocationRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AdminStoreLocationViewModel.class)) {
            return (T) new AdminStoreLocationViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

