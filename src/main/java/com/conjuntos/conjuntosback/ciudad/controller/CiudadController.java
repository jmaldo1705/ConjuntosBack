package com.conjuntos.conjuntosback.ciudad.controller;

import com.conjuntos.conjuntosback.ciudad.dto.CiudadDTO;
import com.conjuntos.conjuntosback.ciudad.service.CiudadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de ciudades
 */
@RestController
@RequestMapping("/api/ciudades")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class CiudadController {

    private final CiudadService ciudadService;

    /**
     * Obtener todas las ciudades activas
     */
    @GetMapping
    public ResponseEntity<List<CiudadDTO>> obtenerCiudades() {
        log.info("GET /api/ciudades - Obteniendo ciudades activas");
        List<CiudadDTO> ciudades = ciudadService.obtenerCiudadesActivas();
        return ResponseEntity.ok(ciudades);
    }

    /**
     * Obtener todas las ciudades (incluyendo inactivas)
     */
    @GetMapping("/todas")
    public ResponseEntity<List<CiudadDTO>> obtenerTodasLasCiudades() {
        log.info("GET /api/ciudades/todas - Obteniendo todas las ciudades");
        List<CiudadDTO> ciudades = ciudadService.obtenerTodasLasCiudades();
        return ResponseEntity.ok(ciudades);
    }

    /**
     * Obtener ciudad por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CiudadDTO> obtenerCiudadPorId(@PathVariable Integer id) {
        log.info("GET /api/ciudades/{} - Obteniendo ciudad", id);
        return ciudadService.obtenerCiudadPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtener departamentos
     */
    @GetMapping("/departamentos")
    public ResponseEntity<List<String>> obtenerDepartamentos() {
        log.info("GET /api/ciudades/departamentos - Obteniendo departamentos");
        List<String> departamentos = ciudadService.obtenerDepartamentos();
        return ResponseEntity.ok(departamentos);
    }

    /**
     * Obtener ciudades por departamento
     */
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<CiudadDTO>> obtenerCiudadesPorDepartamento(@PathVariable String departamento) {
        log.info("GET /api/ciudades/departamento/{} - Obteniendo ciudades del departamento", departamento);
        List<CiudadDTO> ciudades = ciudadService.obtenerCiudadesPorDepartamento(departamento);
        return ResponseEntity.ok(ciudades);
    }

    /**
     * Crear nueva ciudad
     */
    @PostMapping
    public ResponseEntity<CiudadDTO> crearCiudad(@RequestBody CiudadDTO ciudadDTO) {
        log.info("POST /api/ciudades - Creando nueva ciudad");
        try {
            CiudadDTO ciudadCreada = ciudadService.crearCiudad(ciudadDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ciudadCreada);
        } catch (IllegalArgumentException e) {
            log.error("Error al crear ciudad: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualizar ciudad
     */
    @PutMapping("/{id}")
    public ResponseEntity<CiudadDTO> actualizarCiudad(
            @PathVariable Integer id,
            @RequestBody CiudadDTO ciudadDTO) {
        log.info("PUT /api/ciudades/{} - Actualizando ciudad", id);
        try {
            CiudadDTO ciudadActualizada = ciudadService.actualizarCiudad(id, ciudadDTO);
            return ResponseEntity.ok(ciudadActualizada);
        } catch (IllegalArgumentException e) {
            log.error("Error al actualizar ciudad: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar ciudad (eliminación lógica)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCiudad(@PathVariable Integer id) {
        log.info("DELETE /api/ciudades/{} - Eliminando ciudad", id);
        try {
            ciudadService.eliminarCiudad(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error al eliminar ciudad: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
