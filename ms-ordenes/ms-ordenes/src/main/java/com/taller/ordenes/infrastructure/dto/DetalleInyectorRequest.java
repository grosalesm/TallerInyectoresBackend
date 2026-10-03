package com.taller.ordenes.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleInyectorRequest {

    @NotNull(message = "El inyector es obligatorio")
    @Positive(message = "El inyector debe ser válido")
    private Integer idInyector;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;
}