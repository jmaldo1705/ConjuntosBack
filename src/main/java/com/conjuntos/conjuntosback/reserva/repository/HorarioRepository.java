package com.conjuntos.conjuntosback.reserva.repository;

import com.conjuntos.conjuntosback.reserva.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    @Query("SELECT h FROM Horario h WHERE h.zonaComun.id = ?1 AND h.disponible = true ORDER BY h.horaInicio")
    List<Horario> findByZonaComunIdAndDisponibleTrue(Long zonaComunId);

    @Query("SELECT h FROM Horario h WHERE h.zonaComun.id = ?1 ORDER BY h.horaInicio")
    List<Horario> findByZonaComunId(Long zonaComunId);
}
