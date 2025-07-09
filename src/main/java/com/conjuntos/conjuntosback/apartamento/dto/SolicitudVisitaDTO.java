package com.conjuntos.conjuntosback.apartamento.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudVisitaDTO {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotNull(message = "La fecha preferida es requerida")
    private LocalDate fechaPreferida;

    @NotNull(message = "La hora preferida es requerida")
    private LocalTime horaPreferida;

    private String mensaje;
}