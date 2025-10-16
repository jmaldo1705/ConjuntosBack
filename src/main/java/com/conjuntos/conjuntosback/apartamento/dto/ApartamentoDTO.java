package com.conjuntos.conjuntosback.apartamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartamentoDTO {
    private Long id;
    private String tipo;
    private String titulo;
    private BigDecimal precio;
    private Integer habitaciones;
    private Integer banos;
    private BigDecimal area;
    private Integer piso;
    private String torre;
    private String apartamento;
    private String conjunto;
    private String ciudad;
    private String descripcion;
    private List<String> caracteristicas;
    private List<String> imagenes;
    private Boolean disponible;
    private Boolean destacado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}