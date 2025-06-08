package com.conjuntos.conjuntosback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<Map<String, String>> publicEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Public content - No authentication required");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> userEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> response = new HashMap<>();
        response.put("message", "User content - Authentication required");
        response.put("username", auth.getName());
        return ResponseEntity.ok(response);
    }
}