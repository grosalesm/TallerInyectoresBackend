package com.taller.catalogos.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InyectorRequest {

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
    private String marca;
    private String descripcion;
    private Boolean activo;
}