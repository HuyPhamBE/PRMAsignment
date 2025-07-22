package com.example.prmasignment.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.AdminCategoryApi;
import com.example.prmasignment.api.AdminDashboardApi;
import com.example.prmasignment.api.AdminProductApi;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.model.ProductRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProductRepository {

    private final AdminProductApi api;

    public AdminProductRepository(String token) {
        api = ApiClient.getClientWithAuth(token).create(AdminProductApi.class);
    }

    public void getAllProducts(MutableLiveData<List<Product>> productsLiveData) {
        api.getAllProducts().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                productsLiveData.setValue(null);
            }
        });
    }

    public void createProduct(ProductRequest request, MutableLiveData<Product> result) {
        api.createProduct(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                result.setValue(null);
            }
        });
    }

    public void updateProduct(int id, ProductRequest request, MutableLiveData<Product> result) {
        api.updateProduct(id, request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                result.setValue(null);
            }
        });
    }

    public void deleteProduct(int id, MutableLiveData<Boolean> result) {
        api.deleteProduct(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                result.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result.setValue(false);
            }
        });
    }
}
