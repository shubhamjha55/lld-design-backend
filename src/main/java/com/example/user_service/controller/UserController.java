package com.example.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.model.User;
import com.example.user_service.dto.UserProfileDto;
import com.example.user_service.service.UserService;
import com.example.user_service.dto.SignUpRequestDTO;
import com.example.user_service.dto.SignUpResponseDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<SignUpResponseDTO> registerUser(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return userService.registerUser(signUpRequestDTO);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body(new UserProfileDto());
        }

        // Used a Data Transfer Object Model for encapsulation
        UserProfileDto userProfileDto = new UserProfileDto(user);

        return ResponseEntity.ok(userProfileDto);
    }
}
