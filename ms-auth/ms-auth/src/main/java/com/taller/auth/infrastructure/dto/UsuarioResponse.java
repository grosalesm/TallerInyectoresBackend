package com.taller.auth.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private Integer idUsuario;
    private String nombre;
    private String nombreUsuario;
    private Integer idRol;
    private String nombreRol;
    private Boolean activo;
}