
package com.example.prmasignment.model;

public class LoginResponse {
    private boolean authenticated;
    private String token;

    public boolean isAuthenticated() { return authenticated; }
    public String getToken() { return token; }
}
