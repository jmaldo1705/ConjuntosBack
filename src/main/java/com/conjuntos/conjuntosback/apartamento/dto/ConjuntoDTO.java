package com.conjuntos.conjuntosback.apartamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConjuntoDTO {
    private Integer id;
    private String nombre;
    private String ciudad;
    private String sector;
    private Long totalApartamentos;
    private Long apartamentosDisponibles;
}