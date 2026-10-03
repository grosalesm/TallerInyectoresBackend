package com.taller.ordenes.application.port.usecase;

import com.taller.ordenes.domain.bean.DetalleInyector;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DetalleInyectorUseCase {
    Flux<DetalleInyector> listarPorOrden(Integer idOrden);
    Mono<Void> agregar(Integer idOrden, DetalleInyector detalle);
    Mono<Void> eliminar(Integer idDetalle);
}