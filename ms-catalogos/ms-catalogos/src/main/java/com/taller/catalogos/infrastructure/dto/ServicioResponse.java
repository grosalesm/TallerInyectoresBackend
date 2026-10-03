package com.taller.catalogos.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioResponse {
    private Integer idServicio;
    private String nombre;
    private String descripcion;
    private Double precioBase;
    private Boolean activo;
}