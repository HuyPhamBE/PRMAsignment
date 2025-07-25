package com.example.prmasignment.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Cart {
    @SerializedName("id")
    private int cartId;
    
    private int userId;
    private String createdAt;
    private String updatedAt;
    
    @SerializedName("cartItems")
    private List<CartItem> cartItems;

    public Cart() {}

    public Cart(String updatedAt, String createdAt, int userId, int cartId) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.userId = userId;
        this.cartId = cartId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
