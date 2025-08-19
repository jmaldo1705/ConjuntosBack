package com.conjuntos.conjuntosback.emprendimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprendimientoDTO {

    private String id;
    private String nombre;
    private String descripcion;
    private String descripcionCompleta;
    private String categoria;
    private String propietario;
    private ContactoDTO contacto;
    private String ubicacion;
    private String horarios;
    private List<String> imagenes;
    private LocalDateTime fechaCreacion;
    private Boolean activo;
    private BigDecimal rating;
    private PrecioDTO precio;
    private List<String> servicios;
    private Boolean destacado;
    private RedSocialDTO redSocial;
    private String experiencia;
    private List<String> productos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContactoDTO {
        private String telefono;
        private String email;
        private String whatsapp;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrecioDTO {
        private Integer min;
        private Integer max;
        private String moneda;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedSocialDTO {
        private String facebook;
        private String instagram;
        private String tiktok;
        private String website;
    }
}