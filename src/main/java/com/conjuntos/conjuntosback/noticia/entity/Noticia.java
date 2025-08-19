package com.conjuntos.conjuntosback.noticia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "noticias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 50)
    private String fecha;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String resumen;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String contenido;

    @Column(length = 500)
    private String imagen;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDateTime fechaPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridad prioridad = Prioridad.media;

    @Column(columnDefinition = "JSON")
    private String etiquetas; // JSON array

    @Column(name = "conjunto_id", length = 100)
    private String conjuntoId = "sector-inmobiliario";

    @Column(name = "activa")
    private Boolean activa = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaNoticia categoria;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

    public enum Prioridad {
        baja, media, alta
    }
}