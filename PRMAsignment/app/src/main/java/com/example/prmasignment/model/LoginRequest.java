
package com.example.prmasignment.model;

public class LoginRequest {
    private String username;
    private String password;

    // Constructors, getters, setters
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
