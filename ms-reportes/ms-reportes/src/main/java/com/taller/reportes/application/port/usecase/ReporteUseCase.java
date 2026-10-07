package com.taller.reportes.application.port.usecase;

import com.taller.reportes.domain.bean.Dashboard;
import com.taller.reportes.domain.bean.ReporteMensual;

public interface ReporteUseCase {
    ReporteMensual reportePorMes(int mes, int anio);
    Dashboard obtenerDashboard();
}