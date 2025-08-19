package com.conjuntos.conjuntosback.emprendimiento.repository;

import com.conjuntos.conjuntosback.emprendimiento.entity.CategoriaEmprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaEmprendimientoRepository extends JpaRepository<CategoriaEmprendimiento, Long> {

    List<CategoriaEmprendimiento> findByActivaTrue();

    CategoriaEmprendimiento findByNombreAndActivaTrue(String nombre);
}