package com.taller.reportes.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private Integer ordenesTotalHoy;
    private Integer ordenesPendientes;
    private Integer ordenesEnProceso;
    private Integer ordenesTerminadas;
    private Integer ordenesPagadas;
    private Double ingresosMes;
    private Integer totalClientes;
    private List<OrdenResumenDto> ultimasOrdenes;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrdenResumenDto {
        private Integer idOrden;
        private String estado;
        private LocalDateTime fechaIngreso;
        private Double total;
        private String nombreCliente;
        private String nombreMecanico;
    }
}