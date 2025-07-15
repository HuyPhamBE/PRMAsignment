package com.example.prmasignment.api;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.model.ProductRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;
public interface AdminProductApi {
    @GET("api/products")
    Call<List<Product>> getAllProducts();

    @GET("api/products/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @POST("api/products")
    Call<Product> createProduct(@Body ProductRequest request);

    @PUT("api/products/{id}")
    Call<Product> updateProduct(@Path("id") int id, @Body ProductRequest request);

    @DELETE("api/products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);
}
