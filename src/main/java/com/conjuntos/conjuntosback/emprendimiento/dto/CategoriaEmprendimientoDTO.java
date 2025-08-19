package com.conjuntos.conjuntosback.emprendimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEmprendimientoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activa;
}