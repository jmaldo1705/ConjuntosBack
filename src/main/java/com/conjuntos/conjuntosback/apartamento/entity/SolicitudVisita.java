
package com.conjuntos.conjuntosback.apartamento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "solicitudes_visita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudVisita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartamento_id", nullable = false, columnDefinition = "BIGINT")
    private Apartamento apartamento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @Column(name = "fecha_preferida", nullable = false)
    private LocalDate fechaPreferida;

    @Column(name = "hora_preferida", nullable = false)
    private LocalTime horaPreferida;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @PrePersist
    protected void onCreate() {
        fechaSolicitud = LocalDateTime.now();
    }
}
