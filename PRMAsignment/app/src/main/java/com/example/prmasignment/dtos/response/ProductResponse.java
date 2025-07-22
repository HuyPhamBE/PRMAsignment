package com.example.prmasignment.dtos.response;

import com.example.prmasignment.model.Product;
import java.util.List;

public class ProductResponse {
    private String messages;
    private boolean success;
    private List<Product> data;

    public ProductResponse() {
    }

    public ProductResponse(String messages, boolean success, List<Product> data) {
        this.messages = messages;
        this.success = success;
        this.data = data;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
