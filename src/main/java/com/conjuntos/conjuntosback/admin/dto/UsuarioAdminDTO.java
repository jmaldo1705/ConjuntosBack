package com.conjuntos.conjuntosback.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO para gestión de usuarios en el panel de administración
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAdminDTO {
    
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String apartmentNumber;
    private String phoneNumber;
    private Set<String> roles;
    private boolean isActive;
    private String conjuntoId;
    private String conjuntoNombre;
}
