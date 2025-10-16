package com.conjuntos.conjuntosback.reserva.repository;

import com.conjuntos.conjuntosback.reserva.entity.ZonaComun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZonaComunRepository extends JpaRepository<ZonaComun, Long> {

    List<ZonaComun> findByDisponibleTrue();

    @Query("SELECT DISTINCT z FROM ZonaComun z LEFT JOIN FETCH z.horarios WHERE z.disponible = true ORDER BY z.nombre")
    List<ZonaComun> findAllDisponiblesWithHorarios();

    @Query("SELECT DISTINCT z FROM ZonaComun z LEFT JOIN FETCH z.horarios WHERE z.disponible = true AND z.conjuntoId = :conjuntoId ORDER BY z.nombre")
    List<ZonaComun> findAllDisponiblesWithHorariosByConjuntoId(String conjuntoId);

    @Query("SELECT z FROM ZonaComun z LEFT JOIN FETCH z.horarios WHERE z.id = ?1")
    Optional<ZonaComun> findByIdWithHorarios(Long id);
}
