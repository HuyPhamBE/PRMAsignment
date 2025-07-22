package com.example.prmasignment.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.AdminOrderApi;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.model.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderRepository {
    private final AdminOrderApi orderApi;

    public AdminOrderRepository(String token) {
        this.orderApi = ApiClient.getClientWithAuth(token).create(AdminOrderApi.class);
    }

    public LiveData<List<OrderResponse>> getAllOrders() {
        MutableLiveData<List<OrderResponse>> data = new MutableLiveData<>();

        orderApi.getAllOrder().enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
