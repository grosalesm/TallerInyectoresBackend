package com.taller.catalogos.application.port.outservice;

import com.taller.catalogos.domain.bean.Inyector;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InyectorOutService {
    Flux<Inyector> listar();
    Flux<Inyector> listarActivos();
    Mono<Inyector> obtenerPorId(Integer id);
    Mono<Inyector> insertar(Inyector inyector);
    Mono<Inyector> actualizar(Inyector inyector);
    Mono<Void> eliminar(Integer id);
}