package com.taller.catalogos.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mecanico implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idMecanico;
    private String nombres = "";
    private String apellidos = "";
    private String especialidad = "";
    private String telefono = "";
    private Boolean activo = true;
}