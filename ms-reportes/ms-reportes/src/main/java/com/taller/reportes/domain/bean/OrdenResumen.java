package com.taller.reportes.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenResumen implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idOrden;
    private String estado;
    private LocalDateTime fechaIngreso;
    private Double total;
    private String nombreCliente;
    private String nombreMecanico;
}