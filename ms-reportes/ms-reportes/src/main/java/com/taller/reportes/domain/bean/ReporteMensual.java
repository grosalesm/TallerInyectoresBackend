package com.taller.reportes.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteMensual implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer mes;
    private Integer anio;
    private String nombreMes = "";
    private String tipoFiltro = "mes";
    private String fechaFiltro = "";
    private String titulo = "";
    private Integer totalOrdenes = 0;
    private Integer ordenesAtendidas = 0;
    private Double ingresoTotal = 0.0;
    private List<Map<String, Object>> recibos = new ArrayList<>();
    private List<ItemReporte> serviciosMasUsados = new ArrayList<>();
    private List<ItemReporte> inyectoresMasAtendidos = new ArrayList<>();
}