package com.conjuntos.conjuntosback.noticia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaDTO {
    private Long id;
    private String titulo;
    private String fecha;
    private String resumen;
    private String contenido;
    private String imagen;
    private String categoria;
    private String autor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPublicacion;
    private String prioridad;
    private List<String> etiquetas;
    private String conjuntoId;
}