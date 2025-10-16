package com.conjuntos.conjuntosback.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para crear o actualizar una zona común desde administración
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonaComunAdminDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "La capacidad es requerida")
    private Integer capacidad;
    
    private String icono;
    private String color;
    
    @NotNull(message = "La tarifa es requerida")
    private BigDecimal tarifa;
    
    private Boolean disponible;
    private Boolean requiereReserva;
    
    @NotBlank(message = "El conjunto es requerido")
    private String conjuntoId;
}
