package com.conjuntos.conjuntosback.apartamento.service;

import com.conjuntos.conjuntosback.apartamento.dto.ConjuntoDTO;
import com.conjuntos.conjuntosback.apartamento.entity.Conjunto;
import com.conjuntos.conjuntosback.apartamento.repository.ConjuntoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConjuntoService {

    private final ConjuntoRepository conjuntoRepository;

    @Transactional(readOnly = true)
    public List<ConjuntoDTO> obtenerTodosLosConjuntos() {
        // NOTA: Usar ConjuntoResidencial para datos completos con apartamentos
        List<Conjunto> conjuntos = conjuntoRepository.findAll();
        return conjuntos.stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<ConjuntoDTO> obtenerConjuntoPorId(Integer id) {
        return conjuntoRepository.findById(id)
                .map(this::convertirADTO);
    }

    @Transactional(readOnly = true)
    public List<ConjuntoDTO> obtenerConjuntosPorCiudad(String ciudad) {
        // NOTA: Usar ConjuntoResidencial para datos completos con apartamentos
        List<Conjunto> conjuntos = conjuntoRepository.findByCiudad(ciudad);
        return conjuntos.stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConjuntoDTO> obtenerConjuntosPorSector(String sector) {
        // NOTA: Usar ConjuntoResidencial para datos completos con apartamentos
        List<Conjunto> conjuntos = conjuntoRepository.findBySector(sector);
        return conjuntos.stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<String> obtenerCiudades() {
        return conjuntoRepository.findDistinctCiudades();
    }

    @Transactional(readOnly = true)
    public List<String> obtenerSectores() {
        return conjuntoRepository.findDistinctSectores();
    }

    private ConjuntoDTO convertirADTO(Conjunto conjunto) {
        ConjuntoDTO dto = new ConjuntoDTO();
        dto.setId(conjunto.getId());
        dto.setNombre(conjunto.getNombre());
        dto.setCiudad(conjunto.getCiudad());
        dto.setSector(conjunto.getSector());

        // NOTA: La relación con apartamentos fue movida a ConjuntoResidencial
        // No podemos calcular estadísticas desde la entidad Conjunto antigua
        dto.setTotalApartamentos(0L);
        dto.setApartamentosDisponibles(0L);

        return dto;
    }
}