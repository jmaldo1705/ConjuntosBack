package com.conjuntos.conjuntosback.apartamento.repository;

import com.conjuntos.conjuntosback.apartamento.entity.SolicitudInformacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudInformacionRepository extends JpaRepository<SolicitudInformacion, Long> {
}