package com.conjuntos.conjuntosback.noticia.service;

import com.conjuntos.conjuntosback.noticia.dto.CategoriaNoticiaDTO;
import com.conjuntos.conjuntosback.noticia.dto.NoticiaDTO;
import com.conjuntos.conjuntosback.noticia.entity.CategoriaNoticia;
import com.conjuntos.conjuntosback.noticia.entity.Noticia;
import com.conjuntos.conjuntosback.noticia.repository.CategoriaNoticiaRepository;
import com.conjuntos.conjuntosback.noticia.repository.NoticiaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticiaService {

    private final NoticiaRepository noticiaRepository;
    private final CategoriaNoticiaRepository categoriaNoticiaRepository;
    private final ObjectMapper objectMapper;

    /**
     * Obtiene todas las noticias activas
     * @return Lista de noticias ordenadas por fecha de publicación descendente
     */
    public List<NoticiaDTO> obtenerTodasLasNoticias() {
        List<Noticia> noticias = noticiaRepository.findAllActiveWithCategoriaOrderByFechaDesc();
        return noticias.stream()
                .map(this::convertirANoticiaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las categorías de noticias activas
     * @return Lista de categorías ordenadas por orden
     */
    public List<CategoriaNoticiaDTO> obtenerCategorias() {
        List<CategoriaNoticia> categorias = categoriaNoticiaRepository.findByActivaTrueOrderByOrdenAsc();
        return categorias.stream()
                .map(this::convertirACategoriaNoticiaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene noticias por categoría
     * @param categoriaId ID de la categoría
     * @return Lista de noticias de la categoría especificada
     */
    public List<NoticiaDTO> obtenerNoticiasPorCategoria(String categoriaId) {
        List<Noticia> noticias = noticiaRepository.findByActivaTrueAndCategoriaIdOrderByFechaDesc(categoriaId);
        return noticias.stream()
                .map(this::convertirANoticiaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene noticias destacadas (prioridad alta)
     * @return Lista de noticias destacadas
     */
    public List<NoticiaDTO> obtenerNoticiasDestacadas() {
        List<Noticia> noticias = noticiaRepository.findByActivaTrueAndPrioridadAltaOrderByFechaDesc();
        return noticias.stream()
                .map(this::convertirANoticiaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Noticia a NoticiaDTO
     */
    private NoticiaDTO convertirANoticiaDTO(Noticia noticia) {
        NoticiaDTO dto = new NoticiaDTO();
        dto.setId(noticia.getId());
        dto.setTitulo(noticia.getTitulo());
        dto.setFecha(noticia.getFecha());
        dto.setResumen(noticia.getResumen());
        dto.setContenido(noticia.getContenido());
        dto.setImagen(noticia.getImagen());
        dto.setCategoria(noticia.getCategoria().getNombre());
        dto.setAutor(noticia.getAutor());
        dto.setFechaPublicacion(noticia.getFechaPublicacion());
        dto.setPrioridad(noticia.getPrioridad().name());
        dto.setConjuntoId(noticia.getConjuntoId());

        // Convertir etiquetas de JSON a List<String>
        dto.setEtiquetas(convertirJsonAListaString(noticia.getEtiquetas()));

        return dto;
    }

    /**
     * Convierte una entidad CategoriaNoticia a CategoriaNoticiaDTO
     */
    private CategoriaNoticiaDTO convertirACategoriaNoticiaDTO(CategoriaNoticia categoria) {
        CategoriaNoticiaDTO dto = new CategoriaNoticiaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setIcono(categoria.getIcono());
        dto.setActiva(categoria.getActiva());
        dto.setOrden(categoria.getOrden());
        return dto;
    }

    /**
     * Convierte JSON string a List<String>
     */
    private List<String> convertirJsonAListaString(String json) {
        if (json == null || json.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error al convertir JSON a lista: {}", json, e);
            return Collections.emptyList();
        }
    }
}