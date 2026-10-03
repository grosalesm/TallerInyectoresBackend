package com.taller.ordenes.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orden implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idOrden;
    private Integer idCliente;
    private Integer idMecanico;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private String observaciones = "";
    private String estado = "Pendiente";
    private Double total = 0.0;
    private String nombreCliente = "";
    private String nombreMecanico = "";
}