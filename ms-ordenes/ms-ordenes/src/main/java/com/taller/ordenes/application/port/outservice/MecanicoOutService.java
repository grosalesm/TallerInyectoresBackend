package com.taller.ordenes.application.port.outservice;

import reactor.core.publisher.Mono;

public interface MecanicoOutService {
    Mono<Boolean> existeMecanico(Integer idMecanico);
    Mono<String> obtenerNombreMecanico(Integer idMecanico);
}