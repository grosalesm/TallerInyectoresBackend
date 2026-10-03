package com.taller.reportes.application.port.outservice;

import reactor.core.publisher.Flux;

import java.util.Map;

public interface ReciboOutService {
    Flux<Map<String, Object>> listarPorMes(int mes, int anio);
}