package com.example.prmasignment.api;

import com.example.prmasignment.model.DashboardSummary;
import com.example.prmasignment.model.OrderStatus;
import com.example.prmasignment.model.RevenueTrend;
import com.example.prmasignment.model.TopProduct;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AdminDashboardApi {


    @GET("/api/admin/orders_today")
    Call<Long> getTotalOrdersToday();
    @GET("/api/admin/orders_month")
    Call<Long> getOrdersThisMonth();
    @GET("/api/admin/total_revenue")
    Call<BigDecimal> getTotalRevenue(@Query("status") String status);
    @GET("/api/admin/monthly_revenue")
    Call<RevenueTrend> getMonthlyRevenue(
            @Query("status") String status,
            @Query("year") int year
    );
    @GET("/api/admin/orders_status")
    Call<OrderStatus> getOrderStatus();
    @GET("/api/admin/top_selling")
    Call<TopProduct> getTopProducts();
}
