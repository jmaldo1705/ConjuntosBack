package com.conjuntos.conjuntosback.auth.dto;

public record RegisterResponse(
    String message,
    String username,
    String email
) {}