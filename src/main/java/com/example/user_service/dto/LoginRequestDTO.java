package com.example.user_service.dto;

public class LoginRequestDTO {
    private String username;
    private String password;

    // Constructor
    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
