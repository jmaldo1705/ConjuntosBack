package com.conjuntos.conjuntosback.auth.service;

import com.conjuntos.conjuntosback.auth.model.User;
import com.conjuntos.conjuntosback.auth.repository.UserRepository;
import com.conjuntos.conjuntosback.auth.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service for authentication-related operations.
 * Following the Single Responsibility Principle, this service only handles authentication operations.
 */
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param username the username
     * @param password the password
     * @return the JWT token
     */
    public String authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateToken(username);
    }

    /**
     * Registers a new user.
     *
     * @param username the username
     * @param email the email
     * @param password the password
     * @param fullName the full name
     * @param apartmentNumber the apartment number
     * @param phoneNumber the phone number
     * @return the created user
     * @throws RuntimeException if the username or email is already in use
     */
    public User registerUser(String username, String email, String password, String fullName, 
                             String apartmentNumber, String phoneNumber) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(username, email, passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setApartmentNumber(apartmentNumber);
        user.setPhoneNumber(phoneNumber);

        Set<String> roles = new HashSet<>();
        roles.add("USER");
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
