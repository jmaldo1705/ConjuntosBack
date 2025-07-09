package com.conjuntos.conjuntosback.apartamento.repository;

import com.conjuntos.conjuntosback.apartamento.entity.SolicitudVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudVisitaRepository extends JpaRepository<SolicitudVisita, Long> {
}