package com.example.prmasignment.model;

public class ProductImage {
    private int imageId;
    private int productId;
    private String imageUrl;
    private boolean isPrimaryImage;

    public ProductImage(int imageId, boolean isPrimaryImage, String imageUrl, int productId) {
        this.imageId = imageId;
        this.isPrimaryImage = isPrimaryImage;
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isPrimaryImage() {
        return isPrimaryImage;
    }

    public void setPrimaryImage(boolean primaryImage) {
        isPrimaryImage = primaryImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
