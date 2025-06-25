
package com.example.prmasignment.model;

public class RegisterUserRequest {
    private String email;
    private String phoneNumber;
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    // Constructors, getters, setters
    public RegisterUserRequest(String email, String phoneNumber, String username, String firstName, String lastName, String password) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    // Getters here
}
