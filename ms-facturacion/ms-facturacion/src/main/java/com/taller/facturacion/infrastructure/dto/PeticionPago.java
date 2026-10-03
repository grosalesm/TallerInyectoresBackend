package com.taller.facturacion.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeticionPago {

    @NotNull(message = "La orden es obligatoria")
    @Positive(message = "La orden debe ser válida")
    private Integer idOrden;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private String numOperacion;
}