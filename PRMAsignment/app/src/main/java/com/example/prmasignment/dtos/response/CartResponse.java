package com.example.prmasignment.dtos.response;

import com.example.prmasignment.model.CartItem;
import java.util.List;

public class CartResponse {
    private String cartId;
    private String userId;
    private List<CartItemResponse> items;
    private double totalAmount;
    private int totalItems;

    public CartResponse() {}

    // Getters and setters
    public String getCartId() { return cartId; }
    public void setCartId(String cartId) { this.cartId = cartId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<CartItemResponse> getItems() { return items; }
    public void setItems(List<CartItemResponse> items) { this.items = items; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

    public static class CartItemResponse {
        private int cartItemId;
        private int productId;
        private String productName;
        private String productImageUrl;
        private double productPrice;
        private int quantity;
        private double subtotal;

        public CartItemResponse() {}

        // Getters and setters
        public int getCartItemId() { return cartItemId; }
        public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

        public int getProductId() { return productId; }
        public void setProductId(int productId) { this.productId = productId; }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public String getProductImageUrl() { return productImageUrl; }
        public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }

        public double getProductPrice() { return productPrice; }
        public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getSubtotal() { return subtotal; }
        public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    }
}
