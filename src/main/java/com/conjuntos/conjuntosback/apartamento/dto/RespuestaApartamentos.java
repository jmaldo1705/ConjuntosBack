package com.conjuntos.conjuntosback.apartamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaApartamentos {
    private List<ApartamentoDTO> apartamentos;
    private int totalElementos;
    private int totalPaginas;
    private int paginaActual;
    private int limite;
    private boolean tieneSiguiente;
    private boolean tieneAnterior;
}