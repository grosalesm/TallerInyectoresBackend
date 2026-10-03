package com.taller.ordenes.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleServicioResponse {
    private Integer idDetalle;
    private Integer idOrden;
    private Integer idServicio;
    private String nombreServicio;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}