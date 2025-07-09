package com.conjuntos.conjuntosback.apartamento.controller;

import com.conjuntos.conjuntosback.apartamento.dto.ConjuntoDTO;
import com.conjuntos.conjuntosback.apartamento.service.ConjuntoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conjuntos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConjuntoController {

    private final ConjuntoService conjuntoService;

    @GetMapping
    public ResponseEntity<List<ConjuntoDTO>> obtenerConjuntos() {
        List<ConjuntoDTO> conjuntos = conjuntoService.obtenerTodosLosConjuntos();
        return ResponseEntity.ok(conjuntos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConjuntoDTO> obtenerConjuntoPorId(@PathVariable Integer id) {
        return conjuntoService.obtenerConjuntoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<ConjuntoDTO>> obtenerConjuntosPorCiudad(@PathVariable String ciudad) {
        List<ConjuntoDTO> conjuntos = conjuntoService.obtenerConjuntosPorCiudad(ciudad);
        return ResponseEntity.ok(conjuntos);
    }

    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<ConjuntoDTO>> obtenerConjuntosPorSector(@PathVariable String sector) {
        List<ConjuntoDTO> conjuntos = conjuntoService.obtenerConjuntosPorSector(sector);
        return ResponseEntity.ok(conjuntos);
    }

    @GetMapping("/ciudades")
    public ResponseEntity<List<String>> obtenerCiudades() {
        List<String> ciudades = conjuntoService.obtenerCiudades();
        return ResponseEntity.ok(ciudades);
    }

    @GetMapping("/sectores")
    public ResponseEntity<List<String>> obtenerSectores() {
        List<String> sectores = conjuntoService.obtenerSectores();
        return ResponseEntity.ok(sectores);
    }
}