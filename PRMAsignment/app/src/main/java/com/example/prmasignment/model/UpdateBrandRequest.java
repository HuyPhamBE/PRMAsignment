package com.example.prmasignment.model;

public class UpdateBrandRequest {
    private int brandId;
    private String name;
    private String logoUrl;

    public UpdateBrandRequest(int brandId, String name, String logoUrl) {
        this.brandId = brandId;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public int getId() {
        return brandId;
    }

    public void setId(int id) {
        this.brandId = id;
    }

    public String getImageUrl() {
        return logoUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.logoUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
