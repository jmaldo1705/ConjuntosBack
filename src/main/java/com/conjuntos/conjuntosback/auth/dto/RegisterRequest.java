package com.conjuntos.conjuntosback.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(min = 3, max = 20) String username,
    @NotBlank @Size(max = 50) @Email String email,
    @NotBlank @Size(min = 6, max = 40) String password,
    String fullName,
    String apartmentNumber,
    String phoneNumber,
    String conjuntoId
) {}