package com.conjuntos.conjuntosback.apartamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaOperacion {
    private boolean success;
    private String mensaje;
}