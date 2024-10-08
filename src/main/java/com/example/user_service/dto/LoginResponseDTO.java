package com.example.user_service.dto;

import com.example.user_service.model.User;

public class LoginResponseDTO {
    private String message;
    private String token;
    private UserProfileDto user;

    // Constructor
    public LoginResponseDTO(String message, String token, UserProfileDto user) {
        this.message = message;
        this.token = token;
        this.user = user;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserProfileDto getUser() {
        return user;
    }

    public void setUser(UserProfileDto user) {
        this.user = user;
    }
}
