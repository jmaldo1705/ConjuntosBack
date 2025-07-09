package com.conjuntos.conjuntosback.apartamento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_informacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudInformacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "apartamento_id", nullable = false, columnDefinition = "BIGINT")
    private Apartamento apartamento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @PrePersist
    protected void onCreate() {
        fechaSolicitud = LocalDateTime.now();
    }
}
