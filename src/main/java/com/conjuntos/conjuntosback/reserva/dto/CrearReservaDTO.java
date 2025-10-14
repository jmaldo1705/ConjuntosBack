package com.conjuntos.conjuntosback.reserva.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearReservaDTO {
    @NotNull(message = "La zona común es requerida")
    private Long zonaId;

    @NotNull(message = "El horario es requerido")
    private Long horarioId;

    @NotNull(message = "La fecha es requerida")
    private String fecha;  // Format: "yyyy-MM-dd"

    private String nombreEvento;
    private String observaciones;
}
