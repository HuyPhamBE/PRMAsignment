package com.example.prmasignment.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.prmasignment.api.ProductApi;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.api.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class AdminProductRepository {
    private ProductApi productApi;
    private MutableLiveData<List<Product>> productsLiveData;
    private MutableLiveData<String> errorLiveData;
    private MutableLiveData<Boolean> loadingLiveData;

    public AdminProductRepository(String token) {
        productApi = ApiClient.getClientWithAuth(token).create(ProductApi.class);
        productsLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public MutableLiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void loadProducts() {
        loadingLiveData.setValue(true);
        productApi.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful()) {
                    productsLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Failed to load products: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public void createProduct(Product product) {
        loadingLiveData.setValue(true);
        productApi.createProduct(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful()) {
                    loadProducts(); // Refresh the list
                } else {
                    errorLiveData.setValue("Failed to create product: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public void updateProduct(Product product) {
        loadingLiveData.setValue(true);
        productApi.updateProduct(product.getProductId(), product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful()) {
                    loadProducts(); // Refresh the list
                } else {
                    errorLiveData.setValue("Failed to update product: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public void deleteProduct(Long productId) {
        loadingLiveData.setValue(true);
        productApi.deleteProduct(productId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful()) {
                    loadProducts(); // Refresh the list
                } else {
                    errorLiveData.setValue("Failed to delete product: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue("Network error: " + t.getMessage());
            }
        });
    }
}
