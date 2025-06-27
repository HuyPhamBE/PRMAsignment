package com.example.prmasignment.model;

public class ApiResponse<T> {
    private String messages;
    private boolean success;
    private T data;

    public String getMessages() { return messages; }
    public boolean isSuccess() { return success; }
    public T getData() { return data; }
}

