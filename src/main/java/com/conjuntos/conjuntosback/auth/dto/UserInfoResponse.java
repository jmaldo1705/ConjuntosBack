package com.conjuntos.conjuntosback.auth.dto;

import java.util.Set;

public record UserInfoResponse(
    Long id,
    String username,
    String email,
    String fullName,
    String apartmentNumber,
    String phoneNumber,
    Set<String> roles,
    boolean isActive
) {
}