package com.conjuntos.conjuntosback.ciudad.repository;

import com.conjuntos.conjuntosback.ciudad.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestión de ciudades
 */
@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
    
    List<Ciudad> findByActivoTrueOrderByNombreAsc();
    
    @Query("SELECT DISTINCT c.departamento FROM Ciudad c WHERE c.activo = true ORDER BY c.departamento")
    List<String> findDistinctDepartamentos();
    
    List<Ciudad> findByDepartamentoAndActivoTrueOrderByNombreAsc(String departamento);
    
    Optional<Ciudad> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
}
