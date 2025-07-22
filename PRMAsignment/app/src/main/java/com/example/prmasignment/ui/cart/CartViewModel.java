package com.example.prmasignment.ui.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.prmasignment.dtos.request.AddToCartRequest;
import com.example.prmasignment.dtos.request.UpdateCartItemRequest;
import com.example.prmasignment.dtos.response.CartResponse;
import com.example.prmasignment.repository.CartRepository;

public class CartViewModel extends ViewModel {
    private final CartRepository repository;

    public MutableLiveData<CartResponse> cartLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> addToCartResult = new MutableLiveData<>();
    public MutableLiveData<Boolean> updateCartItemResult = new MutableLiveData<>();
    public MutableLiveData<Boolean> removeFromCartResult = new MutableLiveData<>();
    public MutableLiveData<Boolean> clearCartResult = new MutableLiveData<>();

    public CartViewModel(String token) {
        this.repository = new CartRepository(token);
    }

    public void loadUserCart(String userId) {
        repository.getUserCart(userId, cartLiveData);
    }

    public void addToCart(String userId, int productId, int quantity) {
        AddToCartRequest request = new AddToCartRequest(userId, productId, quantity);
        repository.addToCart(request, addToCartResult);
    }

    public void updateCartItem(int cartItemId, int quantity) {
        UpdateCartItemRequest request = new UpdateCartItemRequest(quantity);
        repository.updateCartItem(cartItemId, request, updateCartItemResult);
    }

    public void removeFromCart(int cartItemId) {
        repository.removeFromCart(cartItemId, removeFromCartResult);
    }

    public void clearCart(String userId) {
        repository.clearCart(userId, clearCartResult);
    }
}
