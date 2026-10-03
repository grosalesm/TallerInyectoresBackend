package com.taller.catalogos.application.port.usecase;

import com.taller.catalogos.domain.bean.Servicio;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioUseCase {
    Flux<Servicio> listar();
    Flux<Servicio> listarActivos();
    Mono<Servicio> obtenerPorId(Integer id);
    Mono<Servicio> guardar(Servicio servicio);
    Mono<Void> eliminar(Integer id);
}