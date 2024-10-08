package com.example.user_service.controller;

import com.example.user_service.dto.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.LoginResponseDTO;
import com.example.user_service.config.JwtUtil;
import com.example.user_service.dto.LoginRequestDTO;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        try {
            User user = userService.getUserByUsername(loginRequestDTO.getUsername());
            if (user == null) {
                return ResponseEntity.ok(new LoginResponseDTO("User does not exist. Please Sign up to continue!", null, null));
            }
            if (passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                UserProfileDto userProfileDto = new UserProfileDto(user);
                return ResponseEntity.ok(new LoginResponseDTO("Login successful", token, userProfileDto));
            }
            return ResponseEntity.ok(new LoginResponseDTO("Invalid credentials. Please check your password and try again!", null, null));
        } catch (Exception e) {
            // Log the exception Application Insights or something
            return ResponseEntity.ok(new LoginResponseDTO("Internal server error", null, null));
        }
    }
}

