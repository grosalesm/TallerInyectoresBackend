package com.taller.auth.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaLogin {
    private String token;
    private String nombre;
    private String rol;
    private Integer idUsuario;
}