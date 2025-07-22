package com.example.prmasignment.ui.adminOrder;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AdminOrderViewModelFactory implements ViewModelProvider.Factory {
    private final String token;

    public AdminOrderViewModelFactory(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AdminOrderViewModel(token);
    }
}
