
package com.example.prmasignment.model;

public class RegisterUserResponse {
    private String messages;
    private boolean success;
    private UserData data;

    public static class UserData {
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String username;
        private String phoneNumber;
        private String status;
        private String role;

        // Getters
        public String getRole() { return role; }
    }

    public boolean isSuccess() { return success; }
    public UserData getData() { return data; }
}
