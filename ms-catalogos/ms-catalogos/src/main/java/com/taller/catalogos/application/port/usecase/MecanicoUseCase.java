package com.taller.catalogos.application.port.usecase;

import com.taller.catalogos.domain.bean.Mecanico;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MecanicoUseCase {
    Flux<Mecanico> listar();
    Flux<Mecanico> listarActivos();
    Mono<Mecanico> obtenerPorId(Integer id);
    Mono<Mecanico> guardar(Mecanico mecanico);
    Mono<Void> eliminar(Integer id);
}