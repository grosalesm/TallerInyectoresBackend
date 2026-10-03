package com.taller.ordenes.application.port.outservice;

import reactor.core.publisher.Mono;

public interface CatalogoOutService {
    Mono<Boolean> existeInyector(Integer idInyector);
    Mono<Boolean> existeServicio(Integer idServicio);
    Mono<String> obtenerModeloInyector(Integer idInyector);
    Mono<String> obtenerMarcaInyector(Integer idInyector);
    Mono<String> obtenerNombreServicio(Integer idServicio);
    Mono<Double> obtenerPrecioServicio(Integer idServicio);
}