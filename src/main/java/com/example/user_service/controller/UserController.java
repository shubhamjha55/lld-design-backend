package com.example.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.user_service.dto.SignUpRequestDTO;
import com.example.user_service.dto.SignUpResponseDTO;
import com.example.user_service.dto.UserProfileDto;
import com.example.user_service.model.User;
import com.example.user_service.service.FileService;
import com.example.user_service.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

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

    @PostMapping("/analyze-files")
    public ResponseEntity<?> analyzeFiles(@RequestParam("files") MultipartFile[] files) {
        if (files.length == 0) {
            return ResponseEntity.badRequest().body("No files uploaded.");
        }
        
        try {
            List<String> results = fileService.analyzeFiles(files);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing files: " + e.getMessage());
        }
    }
}
