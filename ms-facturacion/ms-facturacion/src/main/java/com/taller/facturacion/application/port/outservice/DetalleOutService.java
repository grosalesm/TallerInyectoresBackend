package com.taller.facturacion.application.port.outservice;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface DetalleOutService {
    Mono<List<Map<String, Object>>> obtenerInyectoresPorOrden(Integer idOrden);
    Mono<List<Map<String, Object>>> obtenerServiciosPorOrden(Integer idOrden);
}