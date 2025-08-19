package com.conjuntos.conjuntosback.noticia.controller;

import com.conjuntos.conjuntosback.noticia.dto.CategoriaNoticiaDTO;
import com.conjuntos.conjuntosback.noticia.dto.NoticiaDTO;
import com.conjuntos.conjuntosback.noticia.service.NoticiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NoticiaController {

    private final NoticiaService noticiaService;

    /**
     * Obtiene todas las categorías de noticias activas
     * GET /api/categorias-noticias
     * @return Lista de categorías ordenadas por orden
     */
    @GetMapping("/categorias-noticias")
    public ResponseEntity<List<CategoriaNoticiaDTO>> obtenerCategoriasNoticias() {
        List<CategoriaNoticiaDTO> categorias = noticiaService.obtenerCategorias();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Obtiene todas las noticias activas
     * GET /api/noticias
     * @return Lista de noticias ordenadas por fecha de publicación descendente
     */
    @GetMapping("/noticias")
    public ResponseEntity<List<NoticiaDTO>> obtenerNoticias() {
        List<NoticiaDTO> noticias = noticiaService.obtenerTodasLasNoticias();
        return ResponseEntity.ok(noticias);
    }

    /**
     * Obtiene noticias por categoría
     * GET /api/noticias/categoria/{categoriaId}
     * @param categoriaId ID de la categoría
     * @return Lista de noticias de la categoría especificada
     */
    @GetMapping("/noticias/categoria/{categoriaId}")
    public ResponseEntity<List<NoticiaDTO>> obtenerNoticiasPorCategoria(@PathVariable String categoriaId) {
        List<NoticiaDTO> noticias = noticiaService.obtenerNoticiasPorCategoria(categoriaId);
        return ResponseEntity.ok(noticias);
    }

    /**
     * Obtiene noticias destacadas (prioridad alta)
     * GET /api/noticias/destacadas
     * @return Lista de noticias destacadas
     */
    @GetMapping("/noticias/destacadas")
    public ResponseEntity<List<NoticiaDTO>> obtenerNoticiasDestacadas() {
        List<NoticiaDTO> noticias = noticiaService.obtenerNoticiasDestacadas();
        return ResponseEntity.ok(noticias);
    }
}