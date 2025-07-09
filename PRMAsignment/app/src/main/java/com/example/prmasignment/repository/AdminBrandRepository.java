package com.example.prmasignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.AdminBrandApi;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.model.ApiResponse;
import com.example.prmasignment.model.Brand;
import com.example.prmasignment.model.CreateBrandRequest;
import com.example.prmasignment.model.UpdateBrandRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBrandRepository {
    private final AdminBrandApi api;

    public AdminBrandRepository(String token) {
        this.api = ApiClient.getClientWithAuth(token).create(AdminBrandApi.class);
    }

    public void getAllBrands(MutableLiveData<List<Brand>> liveData) {
        api.getAllBrands().enqueue(new Callback<ApiResponse<List<Brand>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Brand>>> call, Response<ApiResponse<List<Brand>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    liveData.postValue(response.body().getData());
                } else {
                    liveData.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Brand>>> call, Throwable t) {
                liveData.postValue(new ArrayList<>());
            }
        });
    }

    public void getBrandById(int brandId, MutableLiveData<Brand> liveData) {
        api.getBrandById(brandId).enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void createBrand(CreateBrandRequest request, MutableLiveData<Brand> liveData) {
        api.createBrand(request).enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void updateBrand(UpdateBrandRequest request, MutableLiveData<Brand> liveData) {
        api.updateBrand(request).enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void deleteBrand(int brandId, MutableLiveData<Boolean> liveData) {
        api.deleteBrand(brandId).enqueue(new Callback<Brand>() {
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                liveData.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                liveData.postValue(false);
            }
        });
    }
}