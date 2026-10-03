package com.taller.auth.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeticionClave {

    @NotBlank(message = "La nueva clave es obligatoria")
    @Size(min = 4, message = "La clave debe tener al menos 4 caracteres")
    private String nuevaClave;
}