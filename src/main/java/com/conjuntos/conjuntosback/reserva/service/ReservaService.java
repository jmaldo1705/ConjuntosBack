package com.conjuntos.conjuntosback.reserva.service;

import com.conjuntos.conjuntosback.auth.model.User;
import com.conjuntos.conjuntosback.reserva.dto.*;
import com.conjuntos.conjuntosback.reserva.entity.Horario;
import com.conjuntos.conjuntosback.reserva.entity.Reserva;
import com.conjuntos.conjuntosback.reserva.entity.ZonaComun;
import com.conjuntos.conjuntosback.reserva.repository.HorarioRepository;
import com.conjuntos.conjuntosback.reserva.repository.ReservaRepository;
import com.conjuntos.conjuntosback.reserva.repository.ZonaComunRepository;
import com.conjuntos.conjuntosback.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ZonaComunRepository zonaComunRepository;
    private final HorarioRepository horarioRepository;
    private final UserRepository userRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional(readOnly = true)
    public List<ZonaComunDTO> obtenerZonasComunes() {
        List<ZonaComun> zonas = zonaComunRepository.findAllDisponiblesWithHorarios();
        return zonas.stream()
                .map(this::convertirZonaComunADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ZonaComunDTO> obtenerZonaComunPorId(Long id) {
        return zonaComunRepository.findByIdWithHorarios(id)
                .map(this::convertirZonaComunADTO);
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerReservasDelUsuario() {
        User usuario = obtenerUsuarioActual();
        List<Reserva> reservas = reservaRepository.findByUsuarioIdWithDetails(usuario.getId());
        return reservas.stream()
                .map(this::convertirReservaADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerReservasPorRangoFecha(String fechaInicio, String fechaFin) {
        LocalDate inicio = LocalDate.parse(fechaInicio, DATE_FORMATTER);
        LocalDate fin = LocalDate.parse(fechaFin, DATE_FORMATTER);
        
        List<Reserva> reservas = reservaRepository.findByFechaBetweenWithDetails(inicio, fin);
        return reservas.stream()
                .map(this::convertirReservaADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerProximasReservas() {
        User usuario = obtenerUsuarioActual();
        List<Reserva> reservas = reservaRepository.findProximasReservasByUsuarioId(usuario.getId());
        return reservas.stream()
                .map(this::convertirReservaADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadisticasReservasDTO obtenerEstadisticas() {
        Long total = reservaRepository.count();
        Long activas = reservaRepository.countReservasActivas();
        Long pendientes = reservaRepository.countByEstado(Reserva.EstadoReserva.PENDIENTE);
        Long canceladas = reservaRepository.countByEstado(Reserva.EstadoReserva.CANCELADA);

        return new EstadisticasReservasDTO(total, activas, pendientes, canceladas);
    }

    @Transactional
    public ReservaDTO crearReserva(CrearReservaDTO dto) {
        User usuario = obtenerUsuarioActual();
        
        // Validar zona común
        ZonaComun zonaComun = zonaComunRepository.findById(dto.getZonaId())
                .orElseThrow(() -> new RuntimeException("Zona común no encontrada"));
        
        if (!zonaComun.getDisponible()) {
            throw new RuntimeException("La zona común no está disponible");
        }

        // Validar horario
        Horario horario = horarioRepository.findById(dto.getHorarioId())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        
        if (!horario.getDisponible()) {
            throw new RuntimeException("El horario no está disponible");
        }

        // Validar fecha
        LocalDate fecha = LocalDate.parse(dto.getFecha(), DATE_FORMATTER);
        if (fecha.isBefore(LocalDate.now())) {
            throw new RuntimeException("No se pueden hacer reservas para fechas pasadas");
        }

        // Validar disponibilidad
        List<Reserva> reservasExistentes = reservaRepository.findByZonaAndHorarioAndFecha(
                dto.getZonaId(), dto.getHorarioId(), fecha);
        
        if (!reservasExistentes.isEmpty()) {
            throw new RuntimeException("Ya existe una reserva para esta zona, horario y fecha");
        }

        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setZonaComun(zonaComun);
        reserva.setHorario(horario);
        reserva.setUsuario(usuario);
        reserva.setFecha(fecha);
        reserva.setNombreEvento(dto.getNombreEvento());
        reserva.setObservaciones(dto.getObservaciones());
        reserva.setEstado(Reserva.EstadoReserva.PENDIENTE);
        reserva.setCostoTotal(horario.getPrecio());
        
        // Establecer fechas de auditoría manualmente
        reserva.setFechaCreacion(LocalDateTime.now());
        reserva.setFechaActualizacion(LocalDateTime.now());
        
        // Campos de auditoría de usuario
        String username = usuario.getUsername();
        reserva.setUsuarioCreacion(username);
        reserva.setUsuarioModificacion(username);

        Reserva reservaGuardada = reservaRepository.save(reserva);
        log.info("Reserva creada: ID={}, Usuario={}, UsuarioCreacion={}, UsuarioModificacion={}, Zona={}, Fecha={}", 
                reservaGuardada.getId(), usuario.getUsername(), 
                reservaGuardada.getUsuarioCreacion(), reservaGuardada.getUsuarioModificacion(),
                zonaComun.getNombre(), fecha);

        return convertirReservaADTO(reservaGuardada);
    }

    @Transactional
    public ReservaDTO confirmarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (reserva.getEstado() != Reserva.EstadoReserva.PENDIENTE) {
            throw new RuntimeException("Solo se pueden confirmar reservas pendientes");
        }

        User usuarioActual = obtenerUsuarioActual();
        reserva.setEstado(Reserva.EstadoReserva.CONFIRMADA);
        reserva.setFechaActualizacion(LocalDateTime.now());
        reserva.setUsuarioModificacion(usuarioActual.getUsername());
        Reserva reservaActualizada = reservaRepository.save(reserva);
        
        log.info("Reserva confirmada: ID={}, UsuarioModificacion={}", reservaId, reservaActualizada.getUsuarioModificacion());
        return convertirReservaADTO(reservaActualizada);
    }

    @Transactional
    public ReservaDTO cancelarReserva(Long reservaId, String motivo) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        User usuarioActual = obtenerUsuarioActual();
        if (!reserva.getUsuario().getId().equals(usuarioActual.getId())) {
            throw new RuntimeException("No tiene permiso para cancelar esta reserva");
        }

        if (reserva.getEstado() == Reserva.EstadoReserva.CANCELADA) {
            throw new RuntimeException("La reserva ya está cancelada");
        }

        reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
        reserva.setFechaCancelacion(LocalDateTime.now());
        reserva.setFechaActualizacion(LocalDateTime.now());
        reserva.setMotivoCancelacion(motivo);
        reserva.setUsuarioModificacion(usuarioActual.getUsername());

        Reserva reservaActualizada = reservaRepository.save(reserva);
        log.info("Reserva cancelada: ID={}, Motivo={}", reservaId, motivo);
        
        return convertirReservaADTO(reservaActualizada);
    }

    @Transactional
    public void eliminarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        User usuarioActual = obtenerUsuarioActual();
        if (!reserva.getUsuario().getId().equals(usuarioActual.getId())) {
            throw new RuntimeException("No tiene permiso para eliminar esta reserva");
        }

        reservaRepository.delete(reserva);
        log.info("Reserva eliminada: ID={}", reservaId);
    }

    private User obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private ZonaComunDTO convertirZonaComunADTO(ZonaComun zona) {
        ZonaComunDTO dto = new ZonaComunDTO();
        dto.setId(zona.getId());
        dto.setNombre(zona.getNombre());
        dto.setDescripcion(zona.getDescripcion());
        dto.setCapacidad(zona.getCapacidad());
        dto.setIcono(zona.getIcono());
        dto.setColor(zona.getColor());
        dto.setTarifa(zona.getTarifa());
        dto.setDisponible(zona.getDisponible());
        dto.setRequiereReserva(zona.getRequiereReserva());

        if (zona.getHorarios() != null) {
            List<HorarioDTO> horarios = zona.getHorarios().stream()
                    .map(this::convertirHorarioADTO)
                    .collect(Collectors.toList());
            dto.setHorarios(horarios);
        }

        return dto;
    }

    private HorarioDTO convertirHorarioADTO(Horario horario) {
        HorarioDTO dto = new HorarioDTO();
        dto.setId(horario.getId());
        dto.setNombre(horario.getNombre());
        dto.setHoraInicio(horario.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")));
        dto.setHoraFin(horario.getHoraFin().format(DateTimeFormatter.ofPattern("HH:mm")));
        dto.setPrecio(horario.getPrecio());
        dto.setDisponible(horario.getDisponible());
        return dto;
    }

    private ReservaDTO convertirReservaADTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        dto.setZonaId(reserva.getZonaComun().getId());
        dto.setZona(reserva.getZonaComun().getNombre());
        dto.setHorarioId(reserva.getHorario().getId());
        
        String horarioStr = String.format("%s (%s - %s)",
                reserva.getHorario().getNombre(),
                reserva.getHorario().getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")),
                reserva.getHorario().getHoraFin().format(DateTimeFormatter.ofPattern("HH:mm")));
        dto.setHorario(horarioStr);
        
        dto.setFecha(reserva.getFecha().format(DATE_FORMATTER));
        dto.setUsuario(reserva.getUsuario().getFullName());
        dto.setNombreEvento(reserva.getNombreEvento());
        dto.setObservaciones(reserva.getObservaciones());
        dto.setEstado(reserva.getEstado().name().toLowerCase());
        dto.setCostoTotal(reserva.getCostoTotal());
        dto.setFechaCreacion(reserva.getFechaCreacion().format(DATETIME_FORMATTER));
        
        if (reserva.getFechaActualizacion() != null) {
            dto.setFechaActualizacion(reserva.getFechaActualizacion().format(DATETIME_FORMATTER));
        }
        
        // Campos de auditoría
        dto.setUsuarioCreacion(reserva.getUsuarioCreacion());
        dto.setUsuarioModificacion(reserva.getUsuarioModificacion());

        return dto;
    }
}
