package com.taller.ordenes.application.port.outservice;

import com.taller.ordenes.domain.bean.DetalleServicio;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DetalleServicioOutService {
    Flux<DetalleServicio> listarPorOrden(Integer idOrden);
    Mono<DetalleServicio> guardar(DetalleServicio detalle);
    Mono<Void> eliminar(Integer idDetalle);
    Mono<DetalleServicio> obtenerPorId(Integer idDetalle);
}