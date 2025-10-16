package com.conjuntos.conjuntosback.apartamento.entity;

import com.conjuntos.conjuntosback.admin.entity.ConjuntoResidencial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "apartamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoApartamento tipo;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer habitaciones;

    @Column(nullable = false)
    private Integer banos;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal area;

    @Column(nullable = false)
    private Integer piso;

    @Column(nullable = false)
    private String torre;

    @Column(nullable = false)
    private String apartamento;

    // Relación con la entidad ConjuntoResidencial
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conjunto_residencial_id", referencedColumnName = "conjunto_id")
    @JsonIgnore
    private ConjuntoResidencial conjuntoResidencial;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String caracteristicas; // JSON array

    @Column(columnDefinition = "TEXT")
    private String imagenes; // JSON array

    @Column(name = "disponible")
    private Boolean disponible = true;

    @Column(name = "destacado")
    private Boolean destacado = false;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public enum TipoApartamento {
        VENTA, ARRIENDO
    }
}