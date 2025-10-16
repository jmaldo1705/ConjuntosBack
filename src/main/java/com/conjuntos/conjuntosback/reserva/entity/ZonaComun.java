package com.conjuntos.conjuntosback.reserva.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "zonas_comunes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonaComun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(length = 20)
    private String icono;

    @Column(length = 50)
    private String color;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifa;

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(name = "requiere_reserva")
    private Boolean requiereReserva = true;

    @Column(name = "conjunto_id", length = 50)
    private String conjuntoId;  // ID del conjunto residencial al que pertenece la zona común

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @OneToMany(mappedBy = "zonaComun", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Horario> horarios;

    @OneToMany(mappedBy = "zonaComun", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reserva> reservas;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
