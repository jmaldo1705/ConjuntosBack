package com.conjuntos.conjuntosback.emprendimiento.repository;

import com.conjuntos.conjuntosback.emprendimiento.entity.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {

    List<Emprendimiento> findByActivoTrue();

    List<Emprendimiento> findByActivoTrueAndDestacadoTrue();

    @Query("SELECT e FROM Emprendimiento e JOIN FETCH e.categoria WHERE e.activo = true")
    List<Emprendimiento> findAllActiveWithCategoria();

    List<Emprendimiento> findByActivoTrueAndCategoria_Id(Long categoriaId);
}