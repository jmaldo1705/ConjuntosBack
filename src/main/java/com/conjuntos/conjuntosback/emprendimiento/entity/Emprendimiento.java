package com.conjuntos.conjuntosback.emprendimiento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "emprendimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emprendimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String descripcionCompleta;

    @Column(nullable = false, length = 100)
    private String propietario;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    private String whatsapp;

    @Column(nullable = false)
    private String ubicacion;

    @Column(columnDefinition = "TEXT")
    private String horarios;

    @Column(columnDefinition = "TEXT")
    private String imagenes; // JSON array de URLs

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(name = "precio_min")
    private Integer precioMin;

    @Column(name = "precio_max")
    private Integer precioMax;

    @Column(name = "moneda", length = 10)
    private String moneda = "COP";

    @Column(columnDefinition = "TEXT")
    private String servicios; // JSON array

    @Column(name = "destacado")
    private Boolean destacado = false;

    private String facebook;
    private String instagram;
    private String tiktok;
    private String website;

    @Column(columnDefinition = "TEXT")
    private String experiencia;

    @Column(columnDefinition = "TEXT")
    private String productos; // JSON array

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEmprendimiento categoria;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}