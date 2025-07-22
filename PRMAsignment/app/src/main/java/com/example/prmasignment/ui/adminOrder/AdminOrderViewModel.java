package com.example.prmasignment.ui.adminOrder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.model.OrderResponse;
import com.example.prmasignment.repository.AdminOrderRepository;

import java.util.List;

public class AdminOrderViewModel extends ViewModel {
    private final AdminOrderRepository repository;
    private final LiveData<List<OrderResponse>> orders;

    public AdminOrderViewModel(String token) {
        repository = new AdminOrderRepository(token);
        orders = repository.getAllOrders();
    }

    public LiveData<List<OrderResponse>> getOrders() {
        return orders;
    }
}
