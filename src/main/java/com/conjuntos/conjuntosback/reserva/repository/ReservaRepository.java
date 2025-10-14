package com.conjuntos.conjuntosback.reserva.repository;

import com.conjuntos.conjuntosback.reserva.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r JOIN FETCH r.zonaComun JOIN FETCH r.horario JOIN FETCH r.usuario " +
           "WHERE r.usuario.id = ?1 ORDER BY r.fecha DESC, r.horario.horaInicio DESC")
    List<Reserva> findByUsuarioIdWithDetails(Long usuarioId);

    @Query("SELECT r FROM Reserva r JOIN FETCH r.zonaComun JOIN FETCH r.horario JOIN FETCH r.usuario " +
           "WHERE r.fecha BETWEEN ?1 AND ?2 ORDER BY r.fecha, r.horario.horaInicio")
    List<Reserva> findByFechaBetweenWithDetails(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT r FROM Reserva r JOIN FETCH r.zonaComun JOIN FETCH r.horario JOIN FETCH r.usuario " +
           "WHERE r.estado = 'CONFIRMADA' AND r.fecha >= CURRENT_DATE ORDER BY r.fecha, r.horario.horaInicio")
    List<Reserva> findReservasActivasWithDetails();

    @Query("SELECT r FROM Reserva r " +
           "WHERE r.zonaComun.id = :zonaId AND r.horario.id = :horarioId AND r.fecha = :fecha " +
           "AND r.estado IN ('PENDIENTE', 'CONFIRMADA')")
    List<Reserva> findByZonaAndHorarioAndFecha(
        @Param("zonaId") Long zonaId,
        @Param("horarioId") Long horarioId,
        @Param("fecha") LocalDate fecha
    );

    Long countByEstado(Reserva.EstadoReserva estado);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.estado = 'CONFIRMADA' AND r.fecha >= CURRENT_DATE")
    Long countReservasActivas();

    @Query("SELECT r FROM Reserva r JOIN FETCH r.zonaComun JOIN FETCH r.horario JOIN FETCH r.usuario " +
           "WHERE r.usuario.id = ?1 AND r.fecha >= CURRENT_DATE ORDER BY r.fecha, r.horario.horaInicio")
    List<Reserva> findProximasReservasByUsuarioId(Long usuarioId);
}
