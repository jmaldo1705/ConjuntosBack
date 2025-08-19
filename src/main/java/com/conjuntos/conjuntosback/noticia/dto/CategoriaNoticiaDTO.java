package com.conjuntos.conjuntosback.noticia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaNoticiaDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private String icono;
    private Boolean activa;
    private Integer orden;
}