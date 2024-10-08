package com.example.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user_service.model.User;
import com.example.user_service.dto.SignUpRequestDTO;
import com.example.user_service.dto.SignUpResponseDTO;
import com.example.user_service.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<SignUpResponseDTO> registerUser(SignUpRequestDTO signUpRequestDTO) {
        try {
            // Check if username exists
            if (userRepository.findByUsername(signUpRequestDTO.getUsername()) != null) {

                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignUpResponseDTO(HttpStatus.CONFLICT.value(), "Username already exists, Please log in to your account!"));
            }

            // Check if email exists
            if (userRepository.findByEmail(signUpRequestDTO.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignUpResponseDTO(HttpStatus.CONFLICT.value(), "User with specified email already exists"));
            }

            // Create new User entity
            User user = new User();
            user.setUsername(signUpRequestDTO.getUsername());
            user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
            user.setEmail(signUpRequestDTO.getEmail());
            user.setFirstName(signUpRequestDTO.getFirstName());
            user.setLastName(signUpRequestDTO.getLastName());

            // Save user to the database
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SignUpResponseDTO(HttpStatus.CREATED.value(), "You have registered successfully, Please log in to continue"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SignUpResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while processing your registration request!"));
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
