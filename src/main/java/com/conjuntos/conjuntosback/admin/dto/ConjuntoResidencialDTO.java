package com.conjuntos.conjuntosback.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear o actualizar un conjunto residencial
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConjuntoResidencialDTO {
    
    @NotBlank(message = "El ID del conjunto es requerido")
    private String conjuntoId;
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    private String descripcion;
    private String direccion;
    private String ciudad;
    private String telefono;
    
    @Email(message = "Email inválido")
    private String email;
    
    private Integer numeroTorres;
    private Integer numeroApartamentos;
    private Boolean activo;
}
