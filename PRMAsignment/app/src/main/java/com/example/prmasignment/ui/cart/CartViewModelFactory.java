package com.example.prmasignment.ui.cart;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CartViewModelFactory implements ViewModelProvider.Factory {
    private final String token;

    public CartViewModelFactory(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CartViewModel.class)) {
            return (T) new CartViewModel(token);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
