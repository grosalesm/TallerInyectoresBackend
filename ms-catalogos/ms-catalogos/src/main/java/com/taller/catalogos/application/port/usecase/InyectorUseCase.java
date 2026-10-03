package com.taller.catalogos.application.port.usecase;

import com.taller.catalogos.domain.bean.Inyector;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InyectorUseCase {
    Flux<Inyector> listar();
    Flux<Inyector> listarActivos();
    Mono<Inyector> obtenerPorId(Integer id);
    Mono<Inyector> guardar(Inyector inyector);
    Mono<Void> eliminar(Integer id);
}