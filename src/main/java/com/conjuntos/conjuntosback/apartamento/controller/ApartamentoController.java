package com.conjuntos.conjuntosback.apartamento.controller;

import com.conjuntos.conjuntosback.apartamento.dto.*;
import com.conjuntos.conjuntosback.apartamento.service.ApartamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/apartamentos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApartamentoController {

    private final ApartamentoService apartamentoService;

    @GetMapping
    public ResponseEntity<RespuestaApartamentos> obtenerApartamentos(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String conjunto,
            @RequestParam(required = false) Integer habitaciones,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @RequestParam(required = false) Boolean disponible,
            @RequestParam(required = false) Boolean destacado,
            @RequestParam(required = false) String busqueda,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int limite) {

        RespuestaApartamentos respuesta = apartamentoService.buscarApartamentos(
                tipo, conjunto, habitaciones, precioMin, precioMax,
                disponible, destacado, busqueda, pagina, limite
        );

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartamentoDTO> obtenerApartamento(@PathVariable Long id) {
        return apartamentoService.obtenerApartamentoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conjuntos")
    public ResponseEntity<List<String>> obtenerConjuntos() {
        List<String> conjuntos = apartamentoService.obtenerConjuntos();
        return ResponseEntity.ok(conjuntos);
    }

    // Nuevo endpoint para obtener información completa de conjuntos
    @GetMapping("/conjuntos/completo")
    public ResponseEntity<List<ConjuntoDTO>> obtenerConjuntosCompleto() {
        List<ConjuntoDTO> conjuntos = apartamentoService.obtenerConjuntosCompleto();
        return ResponseEntity.ok(conjuntos);
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<EstadisticasApartamentos> obtenerEstadisticas() {
        EstadisticasApartamentos estadisticas = apartamentoService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }

    @PostMapping("/{id}/solicitar-info")
    public ResponseEntity<RespuestaOperacion> solicitarInformacion(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudInformacionDTO solicitudDTO) {

        RespuestaOperacion respuesta = apartamentoService.solicitarInformacion(id, solicitudDTO);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/{id}/programar-visita")
    public ResponseEntity<RespuestaOperacion> programarVisita(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudVisitaDTO solicitudDTO) {

        RespuestaOperacion respuesta = apartamentoService.programarVisita(id, solicitudDTO);
        return ResponseEntity.ok(respuesta);
    }
}