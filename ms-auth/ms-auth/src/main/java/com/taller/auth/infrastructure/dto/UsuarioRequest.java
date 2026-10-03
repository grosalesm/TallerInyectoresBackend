package com.taller.auth.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    private String clave;

    @NotNull(message = "El rol es obligatorio")
    @Positive(message = "El rol debe ser válido")
    private Integer idRol;

    private Boolean activo;
}