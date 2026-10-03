package com.taller.catalogos.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InyectorResponse {
    private Integer idInyector;
    private String modelo;
    private String marca;
    private String descripcion;
    private Boolean activo;
}