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
        List<Conjunto> conjuntos = conjuntoRepository.findAllWithApartamentos();
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
        List<Conjunto> conjuntos = conjuntoRepository.findByCiudadWithApartamentos(ciudad);
        return conjuntos.stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConjuntoDTO> obtenerConjuntosPorSector(String sector) {
        List<Conjunto> conjuntos = conjuntoRepository.findBySectorWithApartamentos(sector);
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

        // Calcular estadísticas del conjunto
        if (conjunto.getApartamentos() != null) {
            Long totalApartamentos = (long) conjunto.getApartamentos().size();
            Long apartamentosDisponibles = conjunto.getApartamentos().stream()
                    .mapToLong(apartamento -> apartamento.getDisponible() ? 1 : 0)
                    .sum();

            dto.setTotalApartamentos(totalApartamentos);
            dto.setApartamentosDisponibles(apartamentosDisponibles);
        } else {
            dto.setTotalApartamentos(0L);
            dto.setApartamentosDisponibles(0L);
        }

        return dto;
    }
}