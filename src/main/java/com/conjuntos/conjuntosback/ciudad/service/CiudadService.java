package com.conjuntos.conjuntosback.ciudad.service;

import com.conjuntos.conjuntosback.ciudad.dto.CiudadDTO;
import com.conjuntos.conjuntosback.ciudad.entity.Ciudad;
import com.conjuntos.conjuntosback.ciudad.repository.CiudadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de ciudades
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CiudadService {

    private final CiudadRepository ciudadRepository;

    /**
     * Obtener todas las ciudades activas
     */
    @Transactional(readOnly = true)
    public List<CiudadDTO> obtenerCiudadesActivas() {
        log.info("Obteniendo todas las ciudades activas");
        return ciudadRepository.findByActivoTrueOrderByNombreAsc().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener todas las ciudades
     */
    @Transactional(readOnly = true)
    public List<CiudadDTO> obtenerTodasLasCiudades() {
        log.info("Obteniendo todas las ciudades");
        return ciudadRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtener ciudad por ID
     */
    @Transactional(readOnly = true)
    public Optional<CiudadDTO> obtenerCiudadPorId(Integer id) {
        log.info("Obteniendo ciudad con ID: {}", id);
        return ciudadRepository.findById(id)
                .map(this::convertirADTO);
    }

    /**
     * Obtener ciudad por nombre
     */
    @Transactional(readOnly = true)
    public Optional<CiudadDTO> obtenerCiudadPorNombre(String nombre) {
        log.info("Obteniendo ciudad con nombre: {}", nombre);
        return ciudadRepository.findByNombre(nombre)
                .map(this::convertirADTO);
    }

    /**
     * Obtener departamentos únicos
     */
    @Transactional(readOnly = true)
    public List<String> obtenerDepartamentos() {
        log.info("Obteniendo lista de departamentos");
        return ciudadRepository.findDistinctDepartamentos();
    }

    /**
     * Obtener ciudades por departamento
     */
    @Transactional(readOnly = true)
    public List<CiudadDTO> obtenerCiudadesPorDepartamento(String departamento) {
        log.info("Obteniendo ciudades del departamento: {}", departamento);
        return ciudadRepository.findByDepartamentoAndActivoTrueOrderByNombreAsc(departamento).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Crear nueva ciudad
     */
    @Transactional
    public CiudadDTO crearCiudad(CiudadDTO ciudadDTO) {
        log.info("Creando nueva ciudad: {}", ciudadDTO.getNombre());
        
        if (ciudadRepository.existsByNombre(ciudadDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe una ciudad con el nombre: " + ciudadDTO.getNombre());
        }

        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(ciudadDTO.getNombre());
        ciudad.setDepartamento(ciudadDTO.getDepartamento());
        ciudad.setActivo(ciudadDTO.getActivo() != null ? ciudadDTO.getActivo() : true);

        Ciudad ciudadGuardada = ciudadRepository.save(ciudad);
        log.info("Ciudad creada exitosamente con ID: {}", ciudadGuardada.getId());
        
        return convertirADTO(ciudadGuardada);
    }

    /**
     * Actualizar ciudad
     */
    @Transactional
    public CiudadDTO actualizarCiudad(Integer id, CiudadDTO ciudadDTO) {
        log.info("Actualizando ciudad con ID: {}", id);
        
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ciudad no encontrada con ID: " + id));

        // Verificar si el nuevo nombre ya existe en otra ciudad
        if (!ciudad.getNombre().equals(ciudadDTO.getNombre()) && 
            ciudadRepository.existsByNombre(ciudadDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe una ciudad con el nombre: " + ciudadDTO.getNombre());
        }

        ciudad.setNombre(ciudadDTO.getNombre());
        ciudad.setDepartamento(ciudadDTO.getDepartamento());
        ciudad.setActivo(ciudadDTO.getActivo());

        Ciudad ciudadActualizada = ciudadRepository.save(ciudad);
        log.info("Ciudad actualizada exitosamente: {}", ciudadActualizada.getNombre());
        
        return convertirADTO(ciudadActualizada);
    }

    /**
     * Eliminar ciudad (eliminación lógica)
     */
    @Transactional
    public void eliminarCiudad(Integer id) {
        log.info("Eliminando ciudad con ID: {}", id);
        
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ciudad no encontrada con ID: " + id));

        ciudad.setActivo(false);
        ciudadRepository.save(ciudad);
        
        log.info("Ciudad desactivada exitosamente: {}", ciudad.getNombre());
    }

    /**
     * Convertir entidad a DTO
     */
    private CiudadDTO convertirADTO(Ciudad ciudad) {
        CiudadDTO dto = new CiudadDTO();
        dto.setId(ciudad.getId());
        dto.setNombre(ciudad.getNombre());
        dto.setDepartamento(ciudad.getDepartamento());
        dto.setActivo(ciudad.getActivo());
        return dto;
    }
}
