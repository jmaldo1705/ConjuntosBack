package com.conjuntos.conjuntosback.reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;
    private Long zonaId;
    private String zona;
    private Long horarioId;
    private String horario;
    private String fecha;  // Format: "yyyy-MM-dd"
    private String usuario;
    private String nombreEvento;
    private String observaciones;
    private String estado;  // pendiente, confirmada, cancelada, solicitud_cancelacion
    private BigDecimal costoTotal;
    private String fechaCreacion;
    private String fechaActualizacion;
    private String usuarioCreacion;
    private String usuarioModificacion;
}
