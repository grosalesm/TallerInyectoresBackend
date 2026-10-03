package com.taller.catalogos.application.port.outservice;

import com.taller.catalogos.domain.bean.Mecanico;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MecanicoOutService {
    Flux<Mecanico> listar();
    Flux<Mecanico> listarActivos();
    Mono<Mecanico> obtenerPorId(Integer id);
    Mono<Mecanico> insertar(Mecanico mecanico);
    Mono<Mecanico> actualizar(Mecanico mecanico);
    Mono<Void> eliminar(Integer id);
}