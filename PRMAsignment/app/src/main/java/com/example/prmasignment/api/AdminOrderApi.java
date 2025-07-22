package com.example.prmasignment.api;

import com.example.prmasignment.model.OrderResponse;
import com.example.prmasignment.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AdminOrderApi {

    @GET("/api/order")
    Call<List<OrderResponse>> getAllOrder();

    @GET("api/order/{orderId}")
    Call<OrderResponse> getOrderById(@Path("orderId") int id);
}
