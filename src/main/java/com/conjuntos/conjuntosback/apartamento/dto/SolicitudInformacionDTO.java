package com.conjuntos.conjuntosback.apartamento.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudInformacionDTO {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    private String mensaje;
}