package com.conjuntos.conjuntosback.emprendimiento.service;

import com.conjuntos.conjuntosback.emprendimiento.dto.CategoriaEmprendimientoDTO;
import com.conjuntos.conjuntosback.emprendimiento.dto.EmprendimientoDTO;
import com.conjuntos.conjuntosback.emprendimiento.entity.CategoriaEmprendimiento;
import com.conjuntos.conjuntosback.emprendimiento.entity.Emprendimiento;
import com.conjuntos.conjuntosback.emprendimiento.repository.CategoriaEmprendimientoRepository;
import com.conjuntos.conjuntosback.emprendimiento.repository.EmprendimientoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmprendimientoService {

    private final EmprendimientoRepository emprendimientoRepository;
    private final CategoriaEmprendimientoRepository categoriaRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<EmprendimientoDTO> obtenerTodosLosEmprendimientos() {
        List<Emprendimiento> emprendimientos = emprendimientoRepository.findAllActiveWithCategoria();
        return emprendimientos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmprendimientoDTO> obtenerEmprendimientosDestacados() {
        List<Emprendimiento> emprendimientos = emprendimientoRepository.findByActivoTrueAndDestacadoTrue();
        return emprendimientos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoriaEmprendimientoDTO> obtenerCategorias() {
        List<CategoriaEmprendimiento> categorias = categoriaRepository.findByActivaTrue();
        return categorias.stream()
                .map(this::convertirCategoriaADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmprendimientoDTO> obtenerEmprendimientosPorCategoria(Long categoriaId) {
        List<Emprendimiento> emprendimientos = emprendimientoRepository.findByActivoTrueAndCategoria_Id(categoriaId);
        return emprendimientos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private EmprendimientoDTO convertirADTO(Emprendimiento emprendimiento) {
        EmprendimientoDTO dto = new EmprendimientoDTO();

        dto.setId(String.valueOf(emprendimiento.getId()));
        dto.setNombre(emprendimiento.getNombre());
        dto.setDescripcion(emprendimiento.getDescripcion());
        dto.setDescripcionCompleta(emprendimiento.getDescripcionCompleta());
        dto.setCategoria(emprendimiento.getCategoria().getNombre());
        dto.setPropietario(emprendimiento.getPropietario());

        // Configurar contacto
        EmprendimientoDTO.ContactoDTO contacto = new EmprendimientoDTO.ContactoDTO();
        contacto.setTelefono(emprendimiento.getTelefono());
        contacto.setEmail(emprendimiento.getEmail());
        contacto.setWhatsapp(emprendimiento.getWhatsapp());
        dto.setContacto(contacto);

        dto.setUbicacion(emprendimiento.getUbicacion());
        dto.setHorarios(emprendimiento.getHorarios());
        dto.setImagenes(parsearArrayJSON(emprendimiento.getImagenes()));
        dto.setFechaCreacion(emprendimiento.getFechaCreacion());
        dto.setActivo(emprendimiento.getActivo());
        dto.setRating(emprendimiento.getRating());

        // Configurar precio
        EmprendimientoDTO.PrecioDTO precio = new EmprendimientoDTO.PrecioDTO();
        precio.setMin(emprendimiento.getPrecioMin());
        precio.setMax(emprendimiento.getPrecioMax());
        precio.setMoneda(emprendimiento.getMoneda());
        dto.setPrecio(precio);

        dto.setServicios(parsearArrayJSON(emprendimiento.getServicios()));
        dto.setDestacado(emprendimiento.getDestacado());

        // Configurar redes sociales
        EmprendimientoDTO.RedSocialDTO redSocial = new EmprendimientoDTO.RedSocialDTO();
        redSocial.setFacebook(emprendimiento.getFacebook());
        redSocial.setInstagram(emprendimiento.getInstagram());
        redSocial.setTiktok(emprendimiento.getTiktok());
        redSocial.setWebsite(emprendimiento.getWebsite());
        dto.setRedSocial(redSocial);

        dto.setExperiencia(emprendimiento.getExperiencia());
        dto.setProductos(parsearArrayJSON(emprendimiento.getProductos()));

        return dto;
    }

    private CategoriaEmprendimientoDTO convertirCategoriaADTO(CategoriaEmprendimiento categoria) {
        CategoriaEmprendimientoDTO dto = new CategoriaEmprendimientoDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setActiva(categoria.getActiva());
        return dto;
    }

    private List<String> parsearArrayJSON(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return List.of();
        }

        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            log.warn("Error parsing JSON array: {}", jsonString, e);
            return List.of();
        }
    }
}