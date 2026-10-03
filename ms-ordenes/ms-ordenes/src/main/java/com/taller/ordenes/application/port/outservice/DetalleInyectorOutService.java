package com.taller.ordenes.application.port.outservice;

import com.taller.ordenes.domain.bean.DetalleInyector;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DetalleInyectorOutService {
    Flux<DetalleInyector> listarPorOrden(Integer idOrden);
    Mono<DetalleInyector> guardar(DetalleInyector detalle);
    Mono<Void> eliminar(Integer idDetalle);
    Mono<DetalleInyector> obtenerPorId(Integer idDetalle);
}