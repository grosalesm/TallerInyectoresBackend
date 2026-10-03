package com.taller.reportes.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteResponse {
    private Integer mes;
    private Integer anio;
    private String nombreMes;
    private String tipoFiltro;
    private String fechaFiltro;
    private String titulo;
    private Integer totalOrdenes;
    private Integer ordenesAtendidas;
    private Double ingresoTotal;
    private List<Map<String, Object>> recibos;
    private List<ItemReporteDto> serviciosMasUsados;
    private List<ItemReporteDto> inyectoresMasAtendidos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemReporteDto {
        private String nombre;
        private Integer cantidad;
        private Double total;
    }
}