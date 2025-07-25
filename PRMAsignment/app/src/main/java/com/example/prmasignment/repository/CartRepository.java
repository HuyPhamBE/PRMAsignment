package com.example.prmasignment.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.prmasignment.api.ApiClient;
import com.example.prmasignment.api.CartApi;
import com.example.prmasignment.dtos.request.AddToCartRequest;
import com.example.prmasignment.dtos.request.UpdateCartItemRequest;
import com.example.prmasignment.model.Cart;
import com.example.prmasignment.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {
    private final CartApi cartApi;

    public CartRepository(String token) {
        this.cartApi = ApiClient.getClientWithAuth(token).create(CartApi.class);
    }

    public void getUserCart(String userId, MutableLiveData<Cart> liveData) {
        cartApi.getUserCart(userId).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                android.util.Log.d("CartRepository", "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Cart cart = response.body();
                    android.util.Log.d("CartRepository", "Cart parsed successfully - ID: " + cart.getCartId());
                    android.util.Log.d("CartRepository", "Cart items count: " + (cart.getCartItems() != null ? cart.getCartItems().size() : "null"));
                    liveData.postValue(cart);
                } else {
                    android.util.Log.e("CartRepository", "Response not successful or body is null");
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                android.util.Log.e("CartRepository", "Cart request failed: " + t.getMessage());
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
