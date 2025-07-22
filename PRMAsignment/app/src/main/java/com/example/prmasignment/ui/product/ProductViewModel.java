package com.example.prmasignment.ui.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.dtos.request.ProductRequest;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    
    private final ProductRepository repository;
    
    public MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    public MutableLiveData<Product> productLiveData = new MutableLiveData<>();
    public MutableLiveData<Product> createProductResult = new MutableLiveData<>();
    public MutableLiveData<Product> updateProductResult = new MutableLiveData<>();
    public MutableLiveData<Boolean> deleteProductResult = new MutableLiveData<>();
    
    public ProductViewModel(String token) {
        this.repository = new ProductRepository(token);
    }
    
    public void fetchAllProducts() {
        repository.getAllProducts(productsLiveData);
    }
    
    public void fetchProductById(int productId) {
        repository.getProductById(productId, productLiveData);
    }
    
    public void createProduct(ProductRequest productRequest) {
        repository.createProduct(productRequest, createProductResult);
    }
    
    public void updateProduct(int productId, ProductRequest productRequest) {
        repository.updateProduct(productId, productRequest, updateProductResult);
    }
    
    public void deleteProduct(int productId) {
        repository.deleteProduct(productId, deleteProductResult);
    }
}
