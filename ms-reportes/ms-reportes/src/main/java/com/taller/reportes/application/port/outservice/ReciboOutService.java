package com.taller.reportes.application.port.outservice;

import java.util.List;
import java.util.Map;

public interface ReciboOutService {
    List<Map<String, Object>> listarPorMes(int mes, int anio);
}