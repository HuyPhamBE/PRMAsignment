package com.example.prmasignment.ui.adminProduct;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.repository.AdminProductRepository;
import java.util.List;

public class AdminProductViewModel extends ViewModel {
    private AdminProductRepository repository;

    public AdminProductViewModel(AdminProductRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Product>> getProducts() {
        return repository.getProductsLiveData();
    }

    public LiveData<String> getError() {
        return repository.getErrorLiveData();
    }

    public LiveData<Boolean> getLoading() {
        return repository.getLoadingLiveData();
    }

    public void loadProducts() {
        repository.loadProducts();
    }

    public void createProduct(Product product) {
        repository.createProduct(product);
    }

    public void updateProduct(Product product) {
        repository.updateProduct(product);
    }

    public void deleteProduct(Long productId) {
        repository.deleteProduct(productId);
    }
}
