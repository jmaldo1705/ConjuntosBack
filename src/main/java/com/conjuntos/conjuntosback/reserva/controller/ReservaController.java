package com.conjuntos.conjuntosback.reserva.controller;

import com.conjuntos.conjuntosback.reserva.dto.*;
import com.conjuntos.conjuntosback.reserva.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;

    /**
     * Obtener todas las zonas comunes disponibles con sus horarios
     */
    @GetMapping("/zonas-comunes")
    public ResponseEntity<List<ZonaComunDTO>> obtenerZonasComunes() {
        List<ZonaComunDTO> zonas = reservaService.obtenerZonasComunes();
        return ResponseEntity.ok(zonas);
    }

    /**
     * Obtener una zona común específica por ID
     */
    @GetMapping("/zonas-comunes/{id}")
    public ResponseEntity<ZonaComunDTO> obtenerZonaComunPorId(@PathVariable Long id) {
        return reservaService.obtenerZonaComunPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtener todas las reservas del usuario autenticado
     */
    @GetMapping("/mis-reservas")
    public ResponseEntity<List<ReservaDTO>> obtenerMisReservas() {
        List<ReservaDTO> reservas = reservaService.obtenerReservasDelUsuario();
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtener reservas próximas del usuario
     */
    @GetMapping("/proximas")
    public ResponseEntity<List<ReservaDTO>> obtenerProximasReservas() {
        List<ReservaDTO> reservas = reservaService.obtenerProximasReservas();
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtener reservas por rango de fechas
     */
    @GetMapping("/por-fecha")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorFecha(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorRangoFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtener estadísticas de reservas
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<EstadisticasReservasDTO> obtenerEstadisticas() {
        EstadisticasReservasDTO estadisticas = reservaService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }

    /**
     * Crear una nueva reserva
     */
    @PostMapping
    public ResponseEntity<ReservaDTO> crearReserva(@Valid @RequestBody CrearReservaDTO dto) {
        try {
            ReservaDTO reserva = reservaService.crearReserva(dto);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Confirmar una reserva
     */
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<ReservaDTO> confirmarReserva(@PathVariable Long id) {
        try {
            ReservaDTO reserva = reservaService.confirmarReserva(id);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Cancelar una reserva
     */
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReservaDTO> cancelarReserva(
            @PathVariable Long id,
            @RequestParam(required = false) String motivo) {
        try {
            ReservaDTO reserva = reservaService.cancelarReserva(id, motivo);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Eliminar una reserva
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        try {
            reservaService.eliminarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
