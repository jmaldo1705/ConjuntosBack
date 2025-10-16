package com.conjuntos.conjuntosback.admin.service;

import com.conjuntos.conjuntosback.admin.dto.*;
import com.conjuntos.conjuntosback.admin.entity.ConjuntoResidencial;
import com.conjuntos.conjuntosback.admin.repository.ConjuntoResidencialRepository;
import com.conjuntos.conjuntosback.auth.model.User;
import com.conjuntos.conjuntosback.auth.repository.UserRepository;
import com.conjuntos.conjuntosback.reserva.entity.ZonaComun;
import com.conjuntos.conjuntosback.reserva.repository.ZonaComunRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestión administrativa de conjuntos, usuarios y zonas comunes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final ConjuntoResidencialRepository conjuntoRepository;
    private final UserRepository userRepository;
    private final ZonaComunRepository zonaComunRepository;

    // ========== GESTIÓN DE CONJUNTOS RESIDENCIALES ==========

    @Transactional(readOnly = true)
    public List<ConjuntoResidencialDTO> obtenerTodosLosConjuntos() {
        return conjuntoRepository.findAll().stream()
                .map(this::convertirConjuntoADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConjuntoResidencialDTO obtenerConjuntoPorId(String conjuntoId) {
        ConjuntoResidencial conjunto = conjuntoRepository.findById(conjuntoId)
                .orElseThrow(() -> new RuntimeException("Conjunto no encontrado"));
        return convertirConjuntoADTO(conjunto);
    }

    @Transactional
    public ConjuntoResidencialDTO crearConjunto(ConjuntoResidencialDTO dto) {
        if (conjuntoRepository.existsById(dto.getConjuntoId())) {
            throw new RuntimeException("Ya existe un conjunto con ese ID");
        }

        ConjuntoResidencial conjunto = new ConjuntoResidencial();
        conjunto.setConjuntoId(dto.getConjuntoId());
        conjunto.setNombre(dto.getNombre());
        conjunto.setDescripcion(dto.getDescripcion());
        conjunto.setDireccion(dto.getDireccion());
        conjunto.setCiudad(dto.getCiudad());
        conjunto.setTelefono(dto.getTelefono());
        conjunto.setEmail(dto.getEmail());
        conjunto.setNumeroTorres(dto.getNumeroTorres());
        conjunto.setNumeroApartamentos(dto.getNumeroApartamentos());
        conjunto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        conjunto = conjuntoRepository.save(conjunto);
        log.info("Conjunto creado: {}", conjunto.getConjuntoId());
        
        return convertirConjuntoADTO(conjunto);
    }

    @Transactional
    public ConjuntoResidencialDTO actualizarConjunto(String conjuntoId, ConjuntoResidencialDTO dto) {
        ConjuntoResidencial conjunto = conjuntoRepository.findById(conjuntoId)
                .orElseThrow(() -> new RuntimeException("Conjunto no encontrado"));

        conjunto.setNombre(dto.getNombre());
        conjunto.setDescripcion(dto.getDescripcion());
        conjunto.setDireccion(dto.getDireccion());
        conjunto.setCiudad(dto.getCiudad());
        conjunto.setTelefono(dto.getTelefono());
        conjunto.setEmail(dto.getEmail());
        conjunto.setNumeroTorres(dto.getNumeroTorres());
        conjunto.setNumeroApartamentos(dto.getNumeroApartamentos());
        if (dto.getActivo() != null) {
            conjunto.setActivo(dto.getActivo());
        }

        conjunto = conjuntoRepository.save(conjunto);
        log.info("Conjunto actualizado: {}", conjunto.getConjuntoId());
        
        return convertirConjuntoADTO(conjunto);
    }

    @Transactional
    public void eliminarConjunto(String conjuntoId) {
        ConjuntoResidencial conjunto = conjuntoRepository.findById(conjuntoId)
                .orElseThrow(() -> new RuntimeException("Conjunto no encontrado"));

        // Verificar que no haya usuarios o zonas asociadas
        long usuariosAsociados = userRepository.count();
        // Esta verificación puede mejorarse con una query específica por conjuntoId

        conjuntoRepository.delete(conjunto);
        log.info("Conjunto eliminado: {}", conjuntoId);
    }

    // ========== GESTIÓN DE USUARIOS ==========

    @Transactional(readOnly = true)
    public List<UsuarioAdminDTO> obtenerTodosLosUsuarios() {
        return userRepository.findAll().stream()
                .map(this::convertirUsuarioADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioAdminDTO obtenerUsuarioPorId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirUsuarioADTO(user);
    }

    @Transactional
    public UsuarioAdminDTO actualizarUsuario(Long userId, ActualizarUsuarioDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setApartmentNumber(dto.getApartmentNumber());
        user.setPhoneNumber(dto.getPhoneNumber());
        
        if (dto.getRoles() != null) {
            user.setRoles(dto.getRoles());
        }
        
        if (dto.getIsActive() != null) {
            user.setActive(dto.getIsActive());
        }
        
        if (dto.getConjuntoId() != null) {
            user.setConjuntoId(dto.getConjuntoId());
        }

        user = userRepository.save(user);
        log.info("Usuario actualizado: {}", user.getUsername());
        
        return convertirUsuarioADTO(user);
    }

    @Transactional
    public void eliminarUsuario(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        userRepository.delete(user);
        log.info("Usuario eliminado: {}", user.getUsername());
    }

    // ========== GESTIÓN DE ZONAS COMUNES ==========

    @Transactional(readOnly = true)
    public List<ZonaComunAdminDTO> obtenerTodasLasZonas() {
        return zonaComunRepository.findAll().stream()
                .map(this::convertirZonaADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ZonaComunAdminDTO> obtenerZonasPorConjunto(String conjuntoId) {
        return zonaComunRepository.findAll().stream()
                .filter(zona -> conjuntoId.equals(zona.getConjuntoId()))
                .map(this::convertirZonaADTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ZonaComunAdminDTO crearZonaComun(ZonaComunAdminDTO dto) {
        ZonaComun zona = new ZonaComun();
        zona.setNombre(dto.getNombre());
        zona.setDescripcion(dto.getDescripcion());
        zona.setCapacidad(dto.getCapacidad());
        zona.setIcono(dto.getIcono());
        zona.setColor(dto.getColor());
        zona.setTarifa(dto.getTarifa());
        zona.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);
        zona.setRequiereReserva(dto.getRequiereReserva() != null ? dto.getRequiereReserva() : true);
        zona.setConjuntoId(dto.getConjuntoId());

        zona = zonaComunRepository.save(zona);
        log.info("Zona común creada: {}", zona.getNombre());
        
        return convertirZonaADTO(zona);
    }

    @Transactional
    public ZonaComunAdminDTO actualizarZonaComun(Long zonaId, ZonaComunAdminDTO dto) {
        ZonaComun zona = zonaComunRepository.findById(zonaId)
                .orElseThrow(() -> new RuntimeException("Zona común no encontrada"));

        zona.setNombre(dto.getNombre());
        zona.setDescripcion(dto.getDescripcion());
        zona.setCapacidad(dto.getCapacidad());
        zona.setIcono(dto.getIcono());
        zona.setColor(dto.getColor());
        zona.setTarifa(dto.getTarifa());
        
        if (dto.getDisponible() != null) {
            zona.setDisponible(dto.getDisponible());
        }
        if (dto.getRequiereReserva() != null) {
            zona.setRequiereReserva(dto.getRequiereReserva());
        }
        if (dto.getConjuntoId() != null) {
            zona.setConjuntoId(dto.getConjuntoId());
        }

        zona = zonaComunRepository.save(zona);
        log.info("Zona común actualizada: {}", zona.getNombre());
        
        return convertirZonaADTO(zona);
    }

    @Transactional
    public void eliminarZonaComun(Long zonaId) {
        ZonaComun zona = zonaComunRepository.findById(zonaId)
                .orElseThrow(() -> new RuntimeException("Zona común no encontrada"));

        zonaComunRepository.delete(zona);
        log.info("Zona común eliminada: {}", zona.getNombre());
    }

    // ========== MÉTODOS AUXILIARES ==========

    private ConjuntoResidencialDTO convertirConjuntoADTO(ConjuntoResidencial conjunto) {
        ConjuntoResidencialDTO dto = new ConjuntoResidencialDTO();
        dto.setConjuntoId(conjunto.getConjuntoId());
        dto.setNombre(conjunto.getNombre());
        dto.setDescripcion(conjunto.getDescripcion());
        dto.setDireccion(conjunto.getDireccion());
        dto.setCiudad(conjunto.getCiudad());
        dto.setTelefono(conjunto.getTelefono());
        dto.setEmail(conjunto.getEmail());
        dto.setNumeroTorres(conjunto.getNumeroTorres());
        dto.setNumeroApartamentos(conjunto.getNumeroApartamentos());
        dto.setActivo(conjunto.getActivo());
        return dto;
    }

    private UsuarioAdminDTO convertirUsuarioADTO(User user) {
        UsuarioAdminDTO dto = new UsuarioAdminDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setApartmentNumber(user.getApartmentNumber());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRoles(user.getRoles());
        dto.setActive(user.isActive());
        dto.setConjuntoId(user.getConjuntoId());
        
        // Obtener nombre del conjunto si existe
        if (user.getConjuntoId() != null) {
            conjuntoRepository.findById(user.getConjuntoId())
                    .ifPresent(conjunto -> dto.setConjuntoNombre(conjunto.getNombre()));
        }
        
        return dto;
    }

    private ZonaComunAdminDTO convertirZonaADTO(ZonaComun zona) {
        ZonaComunAdminDTO dto = new ZonaComunAdminDTO();
        dto.setId(zona.getId());
        dto.setNombre(zona.getNombre());
        dto.setDescripcion(zona.getDescripcion());
        dto.setCapacidad(zona.getCapacidad());
        dto.setIcono(zona.getIcono());
        dto.setColor(zona.getColor());
        dto.setTarifa(zona.getTarifa());
        dto.setDisponible(zona.getDisponible());
        dto.setRequiereReserva(zona.getRequiereReserva());
        dto.setConjuntoId(zona.getConjuntoId());
        return dto;
    }
}
