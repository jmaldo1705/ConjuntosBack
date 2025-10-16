package com.conjuntos.conjuntosback.ciudad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferencia de datos de Ciudad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiudadDTO {
    private Integer id;
    private String nombre;
    private String departamento;
    private Boolean activo;
}
