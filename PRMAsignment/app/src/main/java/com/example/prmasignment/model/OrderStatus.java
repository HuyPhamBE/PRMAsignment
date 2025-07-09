package com.example.prmasignment.model;

public class OrderStatus {
    private String status; // Pending, Shipped, Delivered...
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
