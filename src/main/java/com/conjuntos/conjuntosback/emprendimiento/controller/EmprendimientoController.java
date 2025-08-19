package com.conjuntos.conjuntosback.emprendimiento.controller;

import com.conjuntos.conjuntosback.emprendimiento.dto.CategoriaEmprendimientoDTO;
import com.conjuntos.conjuntosback.emprendimiento.dto.EmprendimientoDTO;
import com.conjuntos.conjuntosback.emprendimiento.service.EmprendimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprendimientos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmprendimientoController {

    private final EmprendimientoService emprendimientoService;

    /**
     * Obtiene todos los emprendimientos activos
     * @return Lista de emprendimientos
     */
    @GetMapping
    public ResponseEntity<List<EmprendimientoDTO>> obtenerEmprendimientos() {
        List<EmprendimientoDTO> emprendimientos = emprendimientoService.obtenerTodosLosEmprendimientos();
        return ResponseEntity.ok(emprendimientos);
    }

    /**
     * Obtiene solo los emprendimientos destacados
     * @return Lista de emprendimientos destacados
     */
    @GetMapping("/destacados")
    public ResponseEntity<List<EmprendimientoDTO>> obtenerEmprendimientosDestacados() {
        List<EmprendimientoDTO> emprendimientos = emprendimientoService.obtenerEmprendimientosDestacados();
        return ResponseEntity.ok(emprendimientos);
    }

    /**
     * Obtiene todas las categorías activas para el dropdown
     * @return Lista de categorías
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaEmprendimientoDTO>> obtenerCategorias() {
        List<CategoriaEmprendimientoDTO> categorias = emprendimientoService.obtenerCategorias();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Obtiene emprendimientos por categoría
     * @param categoriaId ID de la categoría
     * @return Lista de emprendimientos de la categoría especificada
     */
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<EmprendimientoDTO>> obtenerEmprendimientosPorCategoria(@PathVariable Long categoriaId) {
        List<EmprendimientoDTO> emprendimientos = emprendimientoService.obtenerEmprendimientosPorCategoria(categoriaId);
        return ResponseEntity.ok(emprendimientos);
    }
}