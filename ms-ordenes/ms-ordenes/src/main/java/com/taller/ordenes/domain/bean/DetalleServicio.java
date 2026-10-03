package com.taller.ordenes.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleServicio implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idDetalle;
    private Integer idOrden;
    private Integer idServicio;
    private String nombreServicio = "";
    private Integer cantidad;
    private Double precioUnitario;

    public Double getSubtotal() {
        if (cantidad == null || precioUnitario == null) return 0.0;
        return cantidad * precioUnitario;
    }
}