package com.example.prmasignment.ui.adminStoreLoction;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prmasignment.model.StoreLocationRequest;
import com.example.prmasignment.model.StoreLocationResponse;
import com.example.prmasignment.model.UpdateStoreLocationRequest;
import com.example.prmasignment.repository.AdminStoreLocationRepository;

import java.util.Collections;
import java.util.List;

public class AdminStoreLocationViewModel extends ViewModel {
    private final AdminStoreLocationRepository repository;

    public MutableLiveData<List<StoreLocationResponse>> storeLocationsLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();

    public MutableLiveData<StoreLocationResponse> createResult = new MutableLiveData<>();
    public MutableLiveData<StoreLocationResponse> updateResult = new MutableLiveData<>();


    public AdminStoreLocationViewModel(AdminStoreLocationRepository repository) {
        this.repository = repository;
    }
    public void createStoreLocation(StoreLocationRequest request) {
        repository.createStoreLocation(request, createResult);
    }

    public void updateStoreLocation(UpdateStoreLocationRequest request) {
        repository.updateStoreLocation(request, updateResult);
    }


    public void fetchStoreLocations() {
        repository.getAllStoreLocations(storeLocationsLiveData);
    }

    public void deleteStoreLocation(int id) {
        repository.deleteStoreLocation(id, deleteResult);
    }
}

