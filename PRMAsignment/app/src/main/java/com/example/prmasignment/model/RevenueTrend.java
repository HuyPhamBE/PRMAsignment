package com.example.prmasignment.model;

public class RevenueTrend {
    private String month; // Ví dụ "2025-06"
    private double revenue;

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
