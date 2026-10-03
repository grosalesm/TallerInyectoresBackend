package com.taller.facturacion.application.port.outservice;

import reactor.core.publisher.Mono;

public interface ClienteOutService {
    Mono<String> obtenerNombreCliente(Integer idCliente);
    Mono<String> obtenerDniCliente(Integer idCliente);
}