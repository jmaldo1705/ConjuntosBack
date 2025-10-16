package com.conjuntos.conjuntosback.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO para actualizar información de usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarUsuarioDTO {
    
    @NotBlank(message = "El email es requerido")
    @Email(message = "Email inválido")
    private String email;
    
    private String fullName;
    private String apartmentNumber;
    private String phoneNumber;
    private Set<String> roles;
    private Boolean isActive;
    private String conjuntoId;
}
