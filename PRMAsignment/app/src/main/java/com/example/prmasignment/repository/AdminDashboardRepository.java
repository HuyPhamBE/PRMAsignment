package com.example.prmasignment.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.example.prmasignment.api.AdminDashboardApi;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.model.ApiResponse;
import com.example.prmasignment.model.OrderStatusResponse;
import com.example.prmasignment.model.TopProduct;
import com.example.prmasignment.model.RevenueTrend;
import com.example.prmasignment.model.OrderStatus;
import com.example.prmasignment.model.TopSellingResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardRepository {

    private final AdminDashboardApi api;

    public AdminDashboardRepository(String token) {
        this.api = ApiClient.getClientWithAuth(token).create(AdminDashboardApi.class);
    }

    public void getTotalOrdersToday(MutableLiveData<Long> liveData) {
        api.getTotalOrdersToday().enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    Log.d("DashboardRepo", "Orders today: " + response.body());
                    liveData.postValue(response.body());
                } else {
                    Log.e("DashboardRepo", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("DashboardRepo", "getTotalOrdersToday failed", t);
            }
        });
    }


    public void getOrdersThisMonth(MutableLiveData<Long> liveData) {
        api.getOrdersThisMonth().enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    Log.d("DashboardRepo", "getOrdersThisMonth today: " + response.body());
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
        api.getTotalRevenue("Cancelled").enqueue(new Callback<BigDecimal>() {
            @Override
            public void onResponse(Call<BigDecimal> call, Response<BigDecimal> response) {
                if (response.isSuccessful()) {
                    Log.d("DashboardRepo", "getTotalRevenue today: " + response.body());
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BigDecimal> call, Throwable t) {
                Log.e("DashboardRepo", "getTotalRevenue failed", t);
            }
        });
    }

//    public void getMonthlyRevenue(MutableLiveData<List<RevenueTrend>> liveData, String status, int year) {
//        api.getMonthlyRevenue(status, year).enqueue(new Callback<List<RevenueTrend>>() {
//            @Override
//            public void onResponse(Call<List<RevenueTrend>> call, Response<List<RevenueTrend>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    liveData.postValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RevenueTrend>> call, Throwable t) {
//                Log.e("DashboardRepo", "getMonthlyRevenue failed", t);
//            }
//        });
//    }

    public void getOrderStatus(MutableLiveData<List<OrderStatus>> liveData) {
        api.getOrderStatus().enqueue(new Callback<ApiResponse<Map<String, Integer>>>() {
            @Override
            public void onResponse(Call<ApiResponse<Map<String, Integer>>> call, Response<ApiResponse<Map<String, Integer>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderStatus> transformed = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : response.body().getData().entrySet()) {
                        transformed.add(new OrderStatus(entry.getKey(), entry.getValue()));
                    }
                    liveData.postValue(transformed);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Map<String, Integer>>> call, Throwable t) {
                Log.e("DashboardRepo", "getOrderStatus failed", t);
            }
        });
    }



    public void getTopProducts(MutableLiveData<List<TopProduct>> liveData) {
        api.getTopProducts().enqueue(new Callback<ApiResponse<Map<String, Integer>>>() {
            @Override
            public void onResponse(Call<ApiResponse<Map<String, Integer>>> call, Response<ApiResponse<Map<String, Integer>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TopProduct> list = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : response.body().getData().entrySet()) {
                        list.add(new TopProduct(entry.getKey(), entry.getValue()));
                    }
                    liveData.postValue(list);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Map<String, Integer>>> call, Throwable t) {
                Log.e("DashboardRepo", "getTopProducts failed", t);
            }
        });
    }


}
