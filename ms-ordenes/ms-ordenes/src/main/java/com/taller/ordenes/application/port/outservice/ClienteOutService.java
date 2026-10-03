package com.taller.ordenes.application.port.outservice;

import reactor.core.publisher.Mono;

public interface ClienteOutService {
    Mono<Boolean> existeCliente(Integer idCliente);
    Mono<String> obtenerNombreCliente(Integer idCliente);
}