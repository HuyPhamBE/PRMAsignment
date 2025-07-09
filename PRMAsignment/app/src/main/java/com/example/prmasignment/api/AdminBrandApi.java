package com.example.prmasignment.api;

import com.example.prmasignment.model.ApiResponse;
import com.example.prmasignment.model.Brand;
import com.example.prmasignment.model.CreateBrandRequest;
import com.example.prmasignment.model.UpdateBrandRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface AdminBrandApi {

    @GET("/api/brands/get_all_brands")
    Call<ApiResponse<List<Brand>>> getAllBrands();

    @GET("/api/brands/{brandId}")
    Call<Brand> getBrandById(@Path("brandId") int brandId);

    @POST("/api/brands/create_brand")
    Call<Brand> createBrand(@Body CreateBrandRequest request);

    @PUT("/api/brands/update_brand")
    Call<Brand> updateBrand(@Body UpdateBrandRequest request);

    @DELETE("/api/brands/{brandId}")
    Call<Brand> deleteBrand(@Path("brandId") int brandId);
}
