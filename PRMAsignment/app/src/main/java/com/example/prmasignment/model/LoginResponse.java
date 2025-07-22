package com.example.prmasignment.model;

public class LoginResponse {
    private boolean authenticated;
    private String username;
    private String role;
    private String token;

    public boolean isAuthenticated() { return authenticated; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getToken() { return token; }
}
