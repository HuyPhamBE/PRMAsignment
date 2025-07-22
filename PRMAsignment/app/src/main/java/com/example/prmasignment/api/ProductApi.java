package com.example.prmasignment.api;

import com.example.prmasignment.dtos.request.ProductRequest;
import com.example.prmasignment.model.ApiResponse;
import com.example.prmasignment.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductApi {
    
    @GET("api/products")
    Call<List<Product>> getAllProducts();
    
    @GET("api/products/{id}")
    Call<Product> getProductById(@Path("id") int productId);
    
    @POST("api/products")
    Call<Product> createProduct(@Body ProductRequest productRequest);
    
    @PUT("api/products/{id}")
    Call<Product> updateProduct(@Path("id") int productId, @Body ProductRequest productRequest);
    
    @DELETE("api/products/{id}")
    Call<Void> deleteProduct(@Path("id") int productId);
}
