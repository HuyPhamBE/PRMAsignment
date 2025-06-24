package com.example.prmasignment.model;

public class Brand {
    private int brandId;
    private String name;
    private String logoUrl;

    public Brand(String name, String logoUrl, int brandId) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.brandId = brandId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
}
