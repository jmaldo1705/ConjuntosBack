package com.conjuntos.conjuntosback.reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDTO {
    private Long id;
    private String nombre;
    private String horaInicio;  // Format: "HH:mm"
    private String horaFin;     // Format: "HH:mm"
    private BigDecimal precio;
    private Boolean disponible;
}
