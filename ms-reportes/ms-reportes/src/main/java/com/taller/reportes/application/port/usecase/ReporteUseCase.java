package com.taller.reportes.application.port.usecase;

import com.taller.reportes.domain.bean.Dashboard;
import com.taller.reportes.domain.bean.ReporteMensual;
import reactor.core.publisher.Mono;

public interface ReporteUseCase {
    Mono<ReporteMensual> reportePorMes(int mes, int anio);
    Mono<Dashboard> obtenerDashboard();
}