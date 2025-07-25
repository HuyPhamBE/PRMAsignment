package com.example.prmasignment.model;

import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("id")
    private int cartItemId;
    
    @SerializedName("cartId")
    private int cartId;
    
    @SerializedName("productId")
    private int productId;
    
    @SerializedName("productName")
    private String productName;
    
    @SerializedName("productPrice")
    private double productPrice;
    
    private int quantity;
    
    @SerializedName("addedAt")
    private String addedAt;

    public CartItem() {}

    public CartItem(int cartItemId, int cartId, int productId, String productName, double productPrice, int quantity, String addedAt) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    // Helper method for adapter
    public double getSubtotal() {
        return productPrice * quantity;
    }
}
