package com.conjuntos.conjuntosback.apartamento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DEPRECADO: Esta entidad será reemplazada por ConjuntoResidencial
 * Mantener solo para compatibilidad durante la migración
 */
@Entity
@Table(name = "conjuntos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conjunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 100)
    private String ciudad;

    @Column(length = 100)
    private String sector;

    // DESHABILITADO: La relación con Apartamento ahora usa ConjuntoResidencial
    // @OneToMany(mappedBy = "conjunto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonIgnore
    // private List<Apartamento> apartamentos;
}