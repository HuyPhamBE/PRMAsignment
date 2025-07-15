package com.example.prmasignment.ui.adminProduct;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.model.Product;
import com.example.prmasignment.model.ProductRequest;
import com.example.prmasignment.repository.AdminProductRepository;

import java.util.List;

public class AdminProductViewModel extends ViewModel {
    private final AdminProductRepository repository;

    public MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    public MutableLiveData<Product> createProductResult = new MutableLiveData<>();
    public MutableLiveData<Product> updateProductResult = new MutableLiveData<>();
    public MutableLiveData<Boolean> deleteProductResult = new MutableLiveData<>();

    public AdminProductViewModel(AdminProductRepository repository) {
        this.repository = repository;
    }

    public void fetchProducts() {
        repository.getAllProducts(productsLiveData);
    }

    public void createProduct(ProductRequest request) {
        repository.createProduct(request, createProductResult);
    }

    public void updateProduct(int id, ProductRequest request) {
        repository.updateProduct(id, request, updateProductResult);
    }

    public void deleteProduct(int id) {
        repository.deleteProduct(id, deleteProductResult);
    }
}
