package com.example.prmasignment.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.api.CartApi;
import com.example.prmasignment.dtos.request.AddToCartRequest;
import com.example.prmasignment.dtos.request.UpdateCartItemRequest;
import com.example.prmasignment.dtos.response.CartResponse;
import com.example.prmasignment.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {
    private final CartApi cartApi;

    public CartRepository(String token) {
        this.cartApi = ApiClient.getClientWithAuth(token).create(CartApi.class);
    }

    public void getUserCart(String userId, MutableLiveData<CartResponse> liveData) {
        cartApi.getUserCart(userId).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void addToCart(AddToCartRequest request, MutableLiveData<Boolean> resultLiveData) {
        cartApi.addToCart(request.getUserId(), request).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                // Backend returns 201 with CartItem data, so just check if response is successful
                resultLiveData.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                resultLiveData.postValue(false);
            }
        });
    }

    public void updateCartItem(int cartItemId, UpdateCartItemRequest request, MutableLiveData<Boolean> resultLiveData) {
        cartApi.updateCartItem(cartItemId, request).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                resultLiveData.postValue(response.isSuccessful() && response.body() != null && response.body().isSuccess());
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                resultLiveData.postValue(false);
            }
        });
    }

    public void removeFromCart(int cartItemId, MutableLiveData<Boolean> resultLiveData) {
        cartApi.removeFromCart(cartItemId).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                resultLiveData.postValue(response.isSuccessful() && response.body() != null && response.body().isSuccess());
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                resultLiveData.postValue(false);
            }
        });
    }

    public void clearCart(String userId, MutableLiveData<Boolean> resultLiveData) {
        cartApi.clearCart(userId).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                resultLiveData.postValue(response.isSuccessful() && response.body() != null && response.body().isSuccess());
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                resultLiveData.postValue(false);
            }
        });
    }
}
