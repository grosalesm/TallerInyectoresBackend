package com.taller.ordenes.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenResponse {
    private Integer idOrden;
    private Integer idCliente;
    private Integer idMecanico;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private String observaciones;
    private String estado;
    private Double total;
    private String nombreCliente;
    private String nombreMecanico;
    private List<DetalleInyectorResponse> inyectores = new ArrayList<>();
    private List<DetalleServicioResponse> servicios = new ArrayList<>();
}