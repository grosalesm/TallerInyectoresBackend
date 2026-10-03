package com.taller.ordenes.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleInyector implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idDetalle;
    private Integer idOrden;
    private Integer idInyector;
    private String modeloInyector = "";
    private String marcaInyector = "";
    private Integer cantidad;
}