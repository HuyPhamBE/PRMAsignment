package com.example.prmasignment.api;

import com.example.prmasignment.dtos.request.AddToCartRequest;
import com.example.prmasignment.dtos.request.UpdateCartItemRequest;
import com.example.prmasignment.dtos.response.CartResponse;
import com.example.prmasignment.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface CartApi {
    @GET("api/cart/user/{userId}")
    Call<CartResponse> getUserCart(@Path("userId") String userId);

    @POST("api/cart/add")
    Call<ApiResponse<Void>> addToCart(@Body AddToCartRequest request);

    @PUT("api/cart/update/{cartItemId}")
    Call<ApiResponse<Void>> updateCartItem(@Path("cartItemId") int cartItemId, @Body UpdateCartItemRequest request);

    @DELETE("api/cart/remove/{cartItemId}")
    Call<ApiResponse<Void>> removeFromCart(@Path("cartItemId") int cartItemId);

    @DELETE("api/cart/clear/{userId}")
    Call<ApiResponse<Void>> clearCart(@Path("userId") String userId);
}
