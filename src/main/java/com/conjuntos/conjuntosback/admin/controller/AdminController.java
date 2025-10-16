package com.conjuntos.conjuntosback.admin.controller;

import com.conjuntos.conjuntosback.admin.dto.*;
import com.conjuntos.conjuntosback.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión administrativa
 * Solo accesible por usuarios con rol ADMIN
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // ========== ENDPOINTS DE CONJUNTOS RESIDENCIALES ==========

    @GetMapping("/conjuntos")
    public ResponseEntity<List<ConjuntoResidencialDTO>> obtenerConjuntos() {
        return ResponseEntity.ok(adminService.obtenerTodosLosConjuntos());
    }

    @GetMapping("/conjuntos/{conjuntoId}")
    public ResponseEntity<ConjuntoResidencialDTO> obtenerConjunto(@PathVariable String conjuntoId) {
        return ResponseEntity.ok(adminService.obtenerConjuntoPorId(conjuntoId));
    }

    @PostMapping("/conjuntos")
    public ResponseEntity<ConjuntoResidencialDTO> crearConjunto(
            @Valid @RequestBody ConjuntoResidencialDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminService.crearConjunto(dto));
    }

    @PutMapping("/conjuntos/{conjuntoId}")
    public ResponseEntity<ConjuntoResidencialDTO> actualizarConjunto(
            @PathVariable String conjuntoId,
            @Valid @RequestBody ConjuntoResidencialDTO dto) {
        return ResponseEntity.ok(adminService.actualizarConjunto(conjuntoId, dto));
    }

    @DeleteMapping("/conjuntos/{conjuntoId}")
    public ResponseEntity<Void> eliminarConjunto(@PathVariable String conjuntoId) {
        adminService.eliminarConjunto(conjuntoId);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE USUARIOS ==========

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioAdminDTO>> obtenerUsuarios() {
        return ResponseEntity.ok(adminService.obtenerTodosLosUsuarios());
    }

    @GetMapping("/usuarios/{userId}")
    public ResponseEntity<UsuarioAdminDTO> obtenerUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.obtenerUsuarioPorId(userId));
    }

    @PutMapping("/usuarios/{userId}")
    public ResponseEntity<UsuarioAdminDTO> actualizarUsuario(
            @PathVariable Long userId,
            @Valid @RequestBody ActualizarUsuarioDTO dto) {
        return ResponseEntity.ok(adminService.actualizarUsuario(userId, dto));
    }

    @DeleteMapping("/usuarios/{userId}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long userId) {
        adminService.eliminarUsuario(userId);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE ZONAS COMUNES ==========

    @GetMapping("/zonas")
    public ResponseEntity<List<ZonaComunAdminDTO>> obtenerZonas() {
        return ResponseEntity.ok(adminService.obtenerTodasLasZonas());
    }

    @GetMapping("/zonas/conjunto/{conjuntoId}")
    public ResponseEntity<List<ZonaComunAdminDTO>> obtenerZonasPorConjunto(
            @PathVariable String conjuntoId) {
        return ResponseEntity.ok(adminService.obtenerZonasPorConjunto(conjuntoId));
    }

    @PostMapping("/zonas")
    public ResponseEntity<ZonaComunAdminDTO> crearZonaComun(
            @Valid @RequestBody ZonaComunAdminDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminService.crearZonaComun(dto));
    }

    @PutMapping("/zonas/{zonaId}")
    public ResponseEntity<ZonaComunAdminDTO> actualizarZonaComun(
            @PathVariable Long zonaId,
            @Valid @RequestBody ZonaComunAdminDTO dto) {
        return ResponseEntity.ok(adminService.actualizarZonaComun(zonaId, dto));
    }

    @DeleteMapping("/zonas/{zonaId}")
    public ResponseEntity<Void> eliminarZonaComun(@PathVariable Long zonaId) {
        adminService.eliminarZonaComun(zonaId);
        return ResponseEntity.noContent().build();
    }
}
