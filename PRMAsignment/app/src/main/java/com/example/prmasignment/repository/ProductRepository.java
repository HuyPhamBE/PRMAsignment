package com.example.prmasignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.api.ProductApi;
import com.example.prmasignment.dtos.request.ProductRequest;
import com.example.prmasignment.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    
    private final ProductApi api;
    
    public ProductRepository(String token) {
        this.api = ApiClient.getClientWithAuth(token).create(ProductApi.class);
    }
    
    public void getAllProducts(MutableLiveData<List<Product>> liveData) {
        api.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                liveData.postValue(new ArrayList<>());
            }
        });
    }
    
    public void getProductById(int productId, MutableLiveData<Product> liveData) {
        api.getProductById(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }
    
    public void createProduct(ProductRequest productRequest, MutableLiveData<Product> resultLiveData) {
        api.createProduct(productRequest).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultLiveData.postValue(response.body());
                } else {
                    resultLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                resultLiveData.postValue(null);
            }
        });
    }
    
    public void updateProduct(int productId, ProductRequest productRequest, MutableLiveData<Product> resultLiveData) {
        api.updateProduct(productId, productRequest).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultLiveData.postValue(response.body());
                } else {
                    resultLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                resultLiveData.postValue(null);
            }
        });
    }
    
    public void deleteProduct(int productId, MutableLiveData<Boolean> resultLiveData) {
        api.deleteProduct(productId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                resultLiveData.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultLiveData.postValue(false);
            }
        });
    }
}
