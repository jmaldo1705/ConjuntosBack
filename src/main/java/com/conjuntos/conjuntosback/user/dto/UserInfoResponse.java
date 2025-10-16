package com.conjuntos.conjuntosback.user.dto;

import java.util.Set;

/**
 * DTO for returning user information.
 * This record encapsulates user data without exposing sensitive information.
 */
public record UserInfoResponse(
    Long id,
    String username,
    String email,
    String fullName,
    String apartmentNumber,
    String phoneNumber,
    Set<String> roles,
    boolean isActive,
    String conjuntoId
) {
}