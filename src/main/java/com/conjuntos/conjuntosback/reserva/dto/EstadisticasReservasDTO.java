package com.conjuntos.conjuntosback.reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasReservasDTO {
    private Long totalReservas;
    private Long reservasActivas;      // confirmadas
    private Long reservasPendientes;
    private Long reservasCanceladas;
}
