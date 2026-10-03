package com.taller.facturacion.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idOrden;
    private Integer idCliente;
    private Integer idMecanico;
    private String estado;
    private Double total;
}