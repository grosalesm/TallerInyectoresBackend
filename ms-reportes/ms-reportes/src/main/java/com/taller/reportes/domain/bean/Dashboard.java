package com.taller.reportes.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer ordenesTotalHoy = 0;
    private Integer ordenesPendientes = 0;
    private Integer ordenesEnProceso = 0;
    private Integer ordenesTerminadas = 0;
    private Integer ordenesPagadas = 0;
    private Double ingresosMes = 0.0;
    private Integer totalClientes = 0;
    private List<OrdenResumen> ultimasOrdenes = new ArrayList<>();
}