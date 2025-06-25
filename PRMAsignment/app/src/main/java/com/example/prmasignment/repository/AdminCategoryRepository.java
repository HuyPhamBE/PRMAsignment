package com.example.prmasignment.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.AdminCategoryApi;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCategoryRepository {

    private final AdminCategoryApi api;

    public AdminCategoryRepository(String token) {
        this.api = ApiClient.getClientWithAuth(token).create(AdminCategoryApi.class);
    }

    public void getCategories(MutableLiveData<List<Category>> liveData) {
        api.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void createCategory(Category category, MutableLiveData<Category> resultLiveData) {
        api.createCategory(category).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultLiveData.postValue(response.body());
                } else {
                    resultLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                resultLiveData.postValue(null);
            }
        });
    }

    public void updateCategory(Category category, MutableLiveData<Category> resultLiveData) {
        api.updateCategory(category).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultLiveData.postValue(response.body());
                } else {
                    resultLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                resultLiveData.postValue(null);
            }
        });
    }

    public void deleteCategory(int id, MutableLiveData<Boolean> resultLiveData) {
        api.deleteCategory(id).enqueue(new Callback<Void>() {
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
