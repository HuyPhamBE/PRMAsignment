package com.example.prmasignment.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.AdminDashboardApi;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.model.TopProduct;
import com.example.prmasignment.model.RevenueTrend;
import com.example.prmasignment.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardRepository {

    private final AdminDashboardApi api;

    public AdminDashboardRepository(String token) {
        this.api = ApiClient.getClientWithAuth(token).create(AdminDashboardApi.class);
    }

    public void getTotalOrdersToday(MutableLiveData<Long> liveData) {
        api.getTotalOrdersToday().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("DashboardRepo", "getTotalOrdersToday failed", t);
            }
        });
    }

    public void getOrdersThisMonth(MutableLiveData<Long> liveData) {
        api.getOrdersThisMonth().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("DashboardRepo", "getOrdersThisMonth failed", t);
            }
        });
    }

    public void getTotalRevenue(MutableLiveData<BigDecimal> liveData, String status) {
        api.getTotalRevenue(status).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BigDecimal> call, Response<BigDecimal> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BigDecimal> call, Throwable t) {
                Log.e("DashboardRepo", "getTotalRevenue failed", t);
            }
        });
    }

    public void getMonthlyRevenue(MutableLiveData<RevenueTrend> liveData, String status, int year) {
        api.getMonthlyRevenue(status, year).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<RevenueTrend> call, Response<RevenueTrend> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RevenueTrend> call, Throwable t) {
                Log.e("DashboardRepo", "getMonthlyRevenue failed", t);
            }
        });
    }

    public void getOrderStatus(MutableLiveData<OrderStatus> liveData) {
        api.getOrderStatus().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<OrderStatus> call, Response<OrderStatus> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderStatus> call, Throwable t) {
                Log.e("DashboardRepo", "getOrderStatus failed", t);
            }
        });
    }

    public void getTopProducts(MutableLiveData<TopProduct> liveData) {
        api.getTopProducts().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<TopProduct> call, Response<TopProduct> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TopProduct> call, Throwable t) {
                Log.e("DashboardRepo", "getTopProducts failed", t);
            }
        });
    }
}
