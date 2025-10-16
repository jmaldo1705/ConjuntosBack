package com.conjuntos.conjuntosback.admin.repository;

import com.conjuntos.conjuntosback.admin.entity.ConjuntoResidencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestión de conjuntos residenciales
 */
@Repository
public interface ConjuntoResidencialRepository extends JpaRepository<ConjuntoResidencial, String> {
    
    List<ConjuntoResidencial> findByActivoTrue();
    
    Optional<ConjuntoResidencial> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
}
