package com.example.prmasignment.model;

public class CreateBrandRequest {
    private String name;
    private String logoUrl;

    public CreateBrandRequest(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return logoUrl;
    }

    public void setImageUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
