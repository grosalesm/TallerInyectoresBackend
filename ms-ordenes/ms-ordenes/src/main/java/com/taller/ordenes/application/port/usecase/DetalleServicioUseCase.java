package com.taller.ordenes.application.port.usecase;

import com.taller.ordenes.domain.bean.DetalleServicio;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DetalleServicioUseCase {
    Flux<DetalleServicio> listarPorOrden(Integer idOrden);
    Mono<Void> agregar(Integer idOrden, DetalleServicio detalle);
    Mono<Void> eliminar(Integer idDetalle);
}