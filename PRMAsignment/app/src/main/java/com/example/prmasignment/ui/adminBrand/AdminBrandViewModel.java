package com.example.prmasignment.ui.adminBrand;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.model.Brand;
import com.example.prmasignment.model.CreateBrandRequest;
import com.example.prmasignment.model.UpdateBrandRequest;
import com.example.prmasignment.repository.AdminBrandRepository;

import java.util.List;

public class AdminBrandViewModel extends ViewModel {
    private final AdminBrandRepository repository;
    private final MutableLiveData<List<Brand>> brands = new MutableLiveData<>();
    private final MutableLiveData<Brand> brandDetail = new MutableLiveData<>();
    private final MutableLiveData<Brand> brandOperationResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();

    public AdminBrandViewModel(AdminBrandRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Brand>> getBrands() {
        return brands;
    }

    public LiveData<Brand> getBrandDetail() {
        return brandDetail;
    }

    public LiveData<Brand> getBrandOperationResult() {
        return brandOperationResult;
    }

    public LiveData<Boolean> getDeleteResult() {
        return deleteResult;
    }

    public void loadAllBrands() {
        repository.getAllBrands(brands);
    }

    public void loadBrandById(int brandId) {
        repository.getBrandById(brandId, brandDetail);
    }

    public void createBrand(CreateBrandRequest request) {
        repository.createBrand(request, brandOperationResult);
    }

    public void updateBrand(UpdateBrandRequest request) {
        repository.updateBrand(request, brandOperationResult);
    }

    public void deleteBrand(int brandId) {
        repository.deleteBrand(brandId, deleteResult);
    }
}