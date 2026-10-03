package com.taller.ordenes.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeticionEstado {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}