package com.taller.ordenes.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenRequest {

    @NotNull(message = "El cliente es obligatorio")
    @Positive(message = "El cliente debe ser válido")
    private Integer idCliente;

    @NotNull(message = "El mecánico es obligatorio")
    @Positive(message = "El mecánico debe ser válido")
    private Integer idMecanico;

    private String observaciones;
}