package com.conjuntos.conjuntosback.noticia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categorias_noticias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaNoticia {

    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 10)
    private String icono;

    @Column(name = "activa")
    private Boolean activa = true;

    @Column(name = "orden")
    private Integer orden = 0;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Noticia> noticias;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}