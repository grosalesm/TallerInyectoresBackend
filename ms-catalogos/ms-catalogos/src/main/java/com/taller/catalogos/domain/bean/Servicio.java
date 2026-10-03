package com.taller.catalogos.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idServicio;
    private String nombre = "";
    private String descripcion = "";
    private Double precioBase = 0.0;
    private Boolean activo = true;
}