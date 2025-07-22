package com.example.prmasignment.api;

import com.example.prmasignment.model.StoreLocationRequest;
import com.example.prmasignment.model.UpdateStoreLocationRequest;
import com.example.prmasignment.model.StoreLocationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface AdminLocationApi {

    // GET all store locations
    @GET("/api/store_location")
    Call<List<StoreLocationResponse>> getAllStoreLocations();

    // GET store location by ID
    @GET("/api/store_location/{id}")
    Call<StoreLocationResponse> getStoreLocation(@Path("id") int id);

    // POST: Create
    @POST("/api/store_location")
    Call<StoreLocationResponse> createStoreLocation(@Body StoreLocationRequest request);

    // PUT: Update
    @PUT("/api/store_location")
    Call<StoreLocationResponse> updateStoreLocation(@Body UpdateStoreLocationRequest request);

    // DELETE by ID
    @DELETE("/api/store_location/{id}")
    Call<Void> deleteStoreLocation(@Path("id") int id);
}
