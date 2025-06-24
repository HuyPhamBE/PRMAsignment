package com.example.prmasignment.model;

public class Category {
    private int categoryId;
    private String name;
    private String description;

    public Category(int categoryId, String description, String name) {
        this.categoryId = categoryId;
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
