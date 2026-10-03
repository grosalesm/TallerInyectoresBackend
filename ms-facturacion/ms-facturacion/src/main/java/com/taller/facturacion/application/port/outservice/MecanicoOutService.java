package com.taller.facturacion.application.port.outservice;

import reactor.core.publisher.Mono;

public interface MecanicoOutService {
    Mono<String> obtenerNombreMecanico(Integer idMecanico);
}