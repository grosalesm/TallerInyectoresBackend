package com.taller.auth.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idUsuario;
    private String nombre = "";
    private String nombreUsuario = "";
    private String clave = "";
    private Integer idRol;
    private String nombreRol = "";
    private Boolean activo = true;
}