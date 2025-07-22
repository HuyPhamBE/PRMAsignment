package com.example.prmasignment.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.AdminLocationApi;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.model.StoreLocationRequest;
import com.example.prmasignment.model.StoreLocationResponse;
import com.example.prmasignment.model.UpdateStoreLocationRequest;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminStoreLocationRepository {

    private final AdminLocationApi api;

    public AdminStoreLocationRepository(String token) {
        this.api = ApiClient.getClientWithAuth(token).create(AdminLocationApi.class);
    }

    // 🔍 Get store location by ID
    public void getStoreLocation(int id, MutableLiveData<StoreLocationResponse> liveData) {
        api.getStoreLocation(id).enqueue(new Callback<StoreLocationResponse>() {
            @Override
            public void onResponse(Call<StoreLocationResponse> call, Response<StoreLocationResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    Log.e("StoreLocationRepo", "Get failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<StoreLocationResponse> call, Throwable t) {
                Log.e("StoreLocationRepo", "Get store location failed", t);
            }
        });
    }

    // ➕ Create new store location
    public void createStoreLocation(StoreLocationRequest request, MutableLiveData<StoreLocationResponse> liveData) {
        api.createStoreLocation(request).enqueue(new Callback<StoreLocationResponse>() {
            @Override
            public void onResponse(Call<StoreLocationResponse> call, Response<StoreLocationResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    Log.e("StoreLocationRepo", "Create failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<StoreLocationResponse> call, Throwable t) {
                Log.e("StoreLocationRepo", "Create store location failed", t);
            }
        });
    }

    // ✏️ Update existing store location
    public void updateStoreLocation(UpdateStoreLocationRequest request, MutableLiveData<StoreLocationResponse> liveData) {
        api.updateStoreLocation(request).enqueue(new Callback<StoreLocationResponse>() {
            @Override
            public void onResponse(Call<StoreLocationResponse> call, Response<StoreLocationResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    Log.e("StoreLocationRepo", "Update failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<StoreLocationResponse> call, Throwable t) {
                Log.e("StoreLocationRepo", "Update store location failed", t);
            }
        });
    }

    // ❌ Delete store location by ID
    public void deleteStoreLocation(int id, MutableLiveData<Boolean> resultLiveData) {
        api.deleteStoreLocation(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                resultLiveData.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("StoreLocationRepo", "Delete store location failed", t);
                resultLiveData.postValue(false);
            }
        });
    }

    public void getAllStoreLocations(MutableLiveData<List<StoreLocationResponse>> liveData) {
        api.getAllStoreLocations().enqueue(new Callback<List<StoreLocationResponse>>() {
            @Override
            public void onResponse(Call<List<StoreLocationResponse>> call, Response<List<StoreLocationResponse>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(Collections.emptyList());
                    Log.e("StoreLocationRepo", "Get all failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<StoreLocationResponse>> call, Throwable t) {
                liveData.postValue(Collections.emptyList());
                Log.e("StoreLocationRepo", "Get all store locations failed", t);
            }
        });
    }

}
