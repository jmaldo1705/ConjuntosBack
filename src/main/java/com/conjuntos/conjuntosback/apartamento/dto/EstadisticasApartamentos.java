package com.conjuntos.conjuntosback.apartamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasApartamentos {
    private Long totalApartamentos;
    private Long apartamentosVenta;
    private Long apartamentosArriendo;
    private Long apartamentosDisponibles;
    private Long apartamentosDestacados;
}