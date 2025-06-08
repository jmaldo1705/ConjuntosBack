package com.conjuntos.conjuntosback.user.controller;

import com.conjuntos.conjuntosback.auth.model.User;
import com.conjuntos.conjuntosback.user.dto.UserInfoResponse;
import com.conjuntos.conjuntosback.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user-related endpoints.
 * Following the Single Responsibility Principle, this controller only handles user-related operations.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to get the current authenticated user's information.
     * This endpoint is only accessible to authenticated users.
     *
     * @return the current user's information
     */
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getApartmentNumber(),
                user.getPhoneNumber(),
                user.getRoles(),
                user.isActive()
        ));
    }
}