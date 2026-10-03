package com.taller.ordenes.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleInyectorResponse {
    private Integer idDetalle;
    private Integer idOrden;
    private Integer idInyector;
    private String modeloInyector;
    private String marcaInyector;
    private Integer cantidad;
}