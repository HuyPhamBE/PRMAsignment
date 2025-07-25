package com.example.prmasignment.api;

import com.example.prmasignment.dtos.request.AddToCartRequest;
import com.example.prmasignment.dtos.request.UpdateCartItemRequest;
import com.example.prmasignment.model.Cart;
import com.example.prmasignment.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface CartApi {
    @GET("api/carts/user/{userId}")
    Call<Cart> getUserCart(@Path("userId") String userId);

    @POST("api/carts/{userId}/add")
    Call<Object> addToCart(@Path("userId") String userId, @Body AddToCartRequest request);

    @PUT("api/carts/items/{cartItemId}")
    Call<ApiResponse<Void>> updateCartItem(@Path("cartItemId") int cartItemId, @Body UpdateCartItemRequest request);

    @DELETE("api/carts/items/{cartItemId}")
    Call<ApiResponse<Void>> removeFromCart(@Path("cartItemId") int cartItemId);

    @DELETE("api/carts/{userId}/items")
    Call<ApiResponse<Void>> clearCart(@Path("userId") String userId);
}
