package com.conjuntos.conjuntosback.reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonaComunDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer capacidad;
    private String icono;
    private String color;
    private BigDecimal tarifa;
    private Boolean disponible;
    private Boolean requiereReserva;
    private String conjuntoId;
    private List<HorarioDTO> horarios;
}
