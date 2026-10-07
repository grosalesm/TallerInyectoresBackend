package com.taller.reportes.infrastructure.controller;

import com.taller.reportes.application.port.usecase.ReporteUseCase;
import com.taller.reportes.domain.bean.Dashboard;
import com.taller.reportes.domain.bean.ItemReporte;
import com.taller.reportes.domain.bean.OrdenResumen;
import com.taller.reportes.domain.bean.ReporteMensual;
import com.taller.reportes.infrastructure.dto.DashboardResponse;
import com.taller.reportes.infrastructure.dto.ReporteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reporte")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteUseCase reporteUseCase;

    @GetMapping("/mensual/{mes}/{anio}")
    public ReporteResponse reporteMensual(@PathVariable int mes, @PathVariable int anio) {
        return toResponse(reporteUseCase.reportePorMes(mes, anio));
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard() {
        return toDashboardResponse(reporteUseCase.obtenerDashboard());
    }

    private ReporteResponse toResponse(ReporteMensual r) {
        ReporteResponse dto = new ReporteResponse();
        dto.setMes(r.getMes());
        dto.setAnio(r.getAnio());
        dto.setNombreMes(r.getNombreMes());
        dto.setTipoFiltro(r.getTipoFiltro());
        dto.setFechaFiltro(r.getFechaFiltro());
        dto.setTitulo(r.getTitulo());
        dto.setTotalOrdenes(r.getTotalOrdenes());
        dto.setOrdenesAtendidas(r.getOrdenesAtendidas());
        dto.setIngresoTotal(r.getIngresoTotal());
        dto.setRecibos(r.getRecibos());
        dto.setServiciosMasUsados(r.getServiciosMasUsados().stream()
                .map(this::toItemDto).collect(Collectors.toList()));
        dto.setInyectoresMasAtendidos(r.getInyectoresMasAtendidos().stream()
                .map(this::toItemDto).collect(Collectors.toList()));
        return dto;
    }

    private ReporteResponse.ItemReporteDto toItemDto(ItemReporte item) {
        return new ReporteResponse.ItemReporteDto(item.getNombre(), item.getCantidad(), item.getTotal());
    }

    private DashboardResponse toDashboardResponse(Dashboard d) {
        DashboardResponse dto = new DashboardResponse();
        dto.setOrdenesTotalHoy(d.getOrdenesTotalHoy());
        dto.setOrdenesPendientes(d.getOrdenesPendientes());
        dto.setOrdenesEnProceso(d.getOrdenesEnProceso());
        dto.setOrdenesTerminadas(d.getOrdenesTerminadas());
        dto.setOrdenesPagadas(d.getOrdenesPagadas());
        dto.setIngresosMes(d.getIngresosMes());
        dto.setTotalClientes(d.getTotalClientes());
        dto.setUltimasOrdenes(d.getUltimasOrdenes().stream()
                .map(this::toOrdenResumenDto).collect(Collectors.toList()));
        return dto;
    }

    private DashboardResponse.OrdenResumenDto toOrdenResumenDto(OrdenResumen o) {
        return new DashboardResponse.OrdenResumenDto(
                o.getIdOrden(), o.getEstado(), o.getFechaIngreso(),
                o.getTotal(), o.getNombreCliente(), o.getNombreMecanico());
    }
}