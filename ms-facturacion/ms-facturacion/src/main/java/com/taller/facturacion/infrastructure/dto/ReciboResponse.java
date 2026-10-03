package com.taller.facturacion.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReciboResponse {
    private Integer idRecibo;
    private Integer idOrden;
    private LocalDateTime fechaPago;
    private Double monto;
    private String metodoPago;
    private String numOperacion;
    private String numeroRecibo;
    private String nombreCliente;
    private String dniCliente;
    private String nombreMecanico;
    private List<Map<String, Object>> inyectores = new ArrayList<>();
    private List<Map<String, Object>> servicios = new ArrayList<>();
}