package com.example.prmasignment.dtos.request;

public class AddToCartRequest {
    private String userId;
    private int productId;
    private int quantity;

    public AddToCartRequest() {}

    public AddToCartRequest(String userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
