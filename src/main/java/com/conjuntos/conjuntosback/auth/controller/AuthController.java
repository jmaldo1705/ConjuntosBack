package com.conjuntos.conjuntosback.auth.controller;

import com.conjuntos.conjuntosback.auth.dto.*;
import com.conjuntos.conjuntosback.auth.model.User;
import com.conjuntos.conjuntosback.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication-related endpoints.
 * Following the Single Responsibility Principle, this controller only handles authentication operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint for user login.
     *
     * @param request the login request containing username and password
     * @return a response containing the JWT token and user information
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.authenticateUser(request.username(), request.password());
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for user registration.
     *
     * @param request the registration request containing user details
     * @return a response confirming successful registration
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.registerUser(
                request.username(),
                request.email(),
                request.password(),
                request.fullName(),
                request.apartmentNumber(),
                request.phoneNumber(),
                request.conjuntoId()
        );

        return ResponseEntity.ok(new RegisterResponse(
                "User registered successfully!",
                user.getUsername(),
                user.getEmail()
        ));
    }
}
