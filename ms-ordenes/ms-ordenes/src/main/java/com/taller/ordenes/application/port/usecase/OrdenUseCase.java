package com.taller.ordenes.application.port.usecase;

import com.taller.ordenes.domain.bean.Orden;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrdenUseCase {
    Flux<Orden> listar();
    Flux<Orden> listarPorEstado(String estado);
    Mono<Orden> obtenerPorId(Integer id);
    Mono<Orden> crear(Orden orden);
    Mono<Void> cambiarEstado(Integer idOrden, String estado);
    Mono<Void> cambiarEstadoPorEvento(Integer idOrden, String estado);
}