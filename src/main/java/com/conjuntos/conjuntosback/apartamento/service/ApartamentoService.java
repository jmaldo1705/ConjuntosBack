
package com.conjuntos.conjuntosback.apartamento.service;

import com.conjuntos.conjuntosback.apartamento.dto.*;
import com.conjuntos.conjuntosback.apartamento.entity.Apartamento;
import com.conjuntos.conjuntosback.apartamento.entity.Conjunto;
import com.conjuntos.conjuntosback.apartamento.entity.SolicitudInformacion;
import com.conjuntos.conjuntosback.apartamento.entity.SolicitudVisita;
import com.conjuntos.conjuntosback.apartamento.repository.ApartamentoRepository;
import com.conjuntos.conjuntosback.apartamento.repository.ConjuntoRepository;
import com.conjuntos.conjuntosback.apartamento.repository.SolicitudInformacionRepository;
import com.conjuntos.conjuntosback.apartamento.repository.SolicitudVisitaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartamentoService {

    private final ApartamentoRepository apartamentoRepository;
    private final ConjuntoRepository conjuntoRepository;
    private final SolicitudInformacionRepository solicitudInformacionRepository;
    private final SolicitudVisitaRepository solicitudVisitaRepository;
    private final ObjectMapper objectMapper;

    public RespuestaApartamentos buscarApartamentos(String tipo, String conjunto, Integer habitaciones,
                                                    BigDecimal precioMin, BigDecimal precioMax,
                                                    Boolean disponible, Boolean destacado, String busqueda,
                                                    int pagina, int limite) {

        // Convertir string a enum si es necesario
        Apartamento.TipoApartamento tipoEnum = null;
        if (tipo != null) {
            try {
                tipoEnum = Apartamento.TipoApartamento.valueOf(tipo.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Tipo de apartamento inválido: {}", tipo);
            }
        }

        Pageable pageable = PageRequest.of(pagina, limite);

        // Usar el nuevo método que filtra por nombre del conjunto
        Page<Apartamento> apartamentosPage = apartamentoRepository.findByFiltros(
                tipoEnum != null ? tipoEnum.name() : null,
                conjunto,
                habitaciones,
                precioMin,
                precioMax,
                disponible,
                destacado,
                busqueda,
                pageable
        );

        List<ApartamentoDTO> apartamentosDTO = apartamentosPage.getContent().stream()
                .map(this::convertirADTO)
                .toList();

        return new RespuestaApartamentos(
                apartamentosDTO,
                (int) apartamentosPage.getTotalElements(),
                apartamentosPage.getTotalPages(),
                pagina,
                limite,
                apartamentosPage.hasNext(),
                apartamentosPage.hasPrevious()
        );
    }

    public Optional<ApartamentoDTO> obtenerApartamentoPorId(Long id) {
        return apartamentoRepository.findById(id)
                .map(this::convertirADTO);
    }

    public List<String> obtenerConjuntos() {
        return apartamentoRepository.findDistinctConjuntos();
    }

    public List<ConjuntoDTO> obtenerConjuntosCompleto() {
        List<Conjunto> conjuntos = conjuntoRepository.findAll();
        return conjuntos.stream()
                .map(this::convertirConjuntoADTO)
                .toList();
    }

    public EstadisticasApartamentos obtenerEstadisticas() {
        Long totalApartamentos = apartamentoRepository.count();
        Long apartamentosVenta = apartamentoRepository.countByTipo(Apartamento.TipoApartamento.VENTA);
        Long apartamentosArriendo = apartamentoRepository.countByTipo(Apartamento.TipoApartamento.ARRIENDO);
        Long apartamentosDisponibles = apartamentoRepository.countByDisponibleTrue();
        Long apartamentosDestacados = apartamentoRepository.countByDestacadoTrue();

        return new EstadisticasApartamentos(
                totalApartamentos,
                apartamentosVenta,
                apartamentosArriendo,
                apartamentosDisponibles,
                apartamentosDestacados
        );
    }

    public RespuestaOperacion solicitarInformacion(Long apartamentoId, SolicitudInformacionDTO solicitudDTO) {
        Optional<Apartamento> apartamentoOpt = apartamentoRepository.findById(apartamentoId);

        if (apartamentoOpt.isEmpty()) {
            return new RespuestaOperacion(false, "Apartamento no encontrado");
        }

        try {
            SolicitudInformacion solicitud = new SolicitudInformacion();
            solicitud.setApartamento(apartamentoOpt.get());
            solicitud.setNombre(solicitudDTO.getNombre());
            solicitud.setTelefono(solicitudDTO.getTelefono());
            solicitud.setEmail(solicitudDTO.getEmail());
            solicitud.setMensaje(solicitudDTO.getMensaje());

            solicitudInformacionRepository.save(solicitud);

            return new RespuestaOperacion(true, "Solicitud de información enviada correctamente");
        } catch (Exception e) {
            log.error("Error al guardar solicitud de información: ", e);
            return new RespuestaOperacion(false, "Error al procesar la solicitud");
        }
    }

    public RespuestaOperacion programarVisita(Long apartamentoId, SolicitudVisitaDTO solicitudDTO) {
        Optional<Apartamento> apartamentoOpt = apartamentoRepository.findById(apartamentoId);

        if (apartamentoOpt.isEmpty()) {
            return new RespuestaOperacion(false, "Apartamento no encontrado");
        }

        try {
            SolicitudVisita solicitud = new SolicitudVisita();
            solicitud.setApartamento(apartamentoOpt.get());
            solicitud.setNombre(solicitudDTO.getNombre());
            solicitud.setTelefono(solicitudDTO.getTelefono());
            solicitud.setEmail(solicitudDTO.getEmail());
            solicitud.setFechaPreferida(solicitudDTO.getFechaPreferida());
            solicitud.setHoraPreferida(solicitudDTO.getHoraPreferida());
            solicitud.setMensaje(solicitudDTO.getMensaje());

            solicitudVisitaRepository.save(solicitud);

            return new RespuestaOperacion(true, "Visita programada correctamente");
        } catch (Exception e) {
            log.error("Error al programar visita: ", e);
            return new RespuestaOperacion(false, "Error al procesar la solicitud");
        }
    }

    private ApartamentoDTO convertirADTO(Apartamento apartamento) {
        ApartamentoDTO dto = new ApartamentoDTO();
        dto.setId(apartamento.getId());
        dto.setTipo(apartamento.getTipo().name().toLowerCase());
        dto.setTitulo(apartamento.getTitulo());
        dto.setPrecio(apartamento.getPrecio());
        dto.setHabitaciones(apartamento.getHabitaciones());
        dto.setBanos(apartamento.getBanos());
        dto.setArea(apartamento.getArea());
        dto.setPiso(apartamento.getPiso());
        dto.setTorre(apartamento.getTorre());
        dto.setApartamento(apartamento.getApartamento());

        // Ahora obtenemos el conjunto desde la relación
        dto.setConjunto(apartamento.getConjunto().getNombre());

        dto.setDescripcion(apartamento.getDescripcion());
        dto.setCaracteristicas(parseJsonToList(apartamento.getCaracteristicas()));
        dto.setImagenes(parseJsonToList(apartamento.getImagenes()));
        dto.setDisponible(apartamento.getDisponible());
        dto.setDestacado(apartamento.getDestacado());
        dto.setFechaCreacion(apartamento.getFechaCreacion());
        dto.setFechaActualizacion(apartamento.getFechaActualizacion());

        return dto;
    }

    private ConjuntoDTO convertirConjuntoADTO(Conjunto conjunto) {
        ConjuntoDTO dto = new ConjuntoDTO();
        dto.setId(conjunto.getId());
        dto.setNombre(conjunto.getNombre());
        dto.setCiudad(conjunto.getCiudad());
        dto.setSector(conjunto.getSector());

        // Calcular estadísticas del conjunto
        Long totalApartamentos = (long) conjunto.getApartamentos().size();
        Long apartamentosDisponibles = conjunto.getApartamentos().stream()
                .mapToLong(apartamento -> apartamento.getDisponible() ? 1 : 0)
                .sum();

        dto.setTotalApartamentos(totalApartamentos);
        dto.setApartamentosDisponibles(apartamentosDisponibles);

        return dto;
    }

    private List<String> parseJsonToList(String json) {
        if (json == null || json.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.warn("Error al parsear JSON: {}", json, e);
            return new ArrayList<>();
        }
    }
}