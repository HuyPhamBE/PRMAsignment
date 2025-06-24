package com.example.prmasignment.model;

public class Product {
    private int productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String imageUrl;
    private int categoryId;
    private int brandId;
    private String petType;
    private String lifeStage;
    private String specialNeeds;
    private double weightKg;
    private String createdAt;
    private String updatedAt;

    public Product(int productId, String updatedAt, String createdAt, double weightKg, String specialNeeds, String lifeStage, String petType, int brandId, int categoryId, String imageUrl, int stockQuantity, double price, String description, String name) {
        this.productId = productId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.weightKg = weightKg;
        this.specialNeeds = specialNeeds;
        this.lifeStage = lifeStage;
        this.petType = petType;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.description = description;
        this.name = name;
    }
}
