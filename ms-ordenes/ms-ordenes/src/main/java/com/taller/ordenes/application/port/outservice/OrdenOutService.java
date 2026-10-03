package com.taller.ordenes.application.port.outservice;

import com.taller.ordenes.domain.bean.Orden;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrdenOutService {
    Flux<Orden> listar();
    Flux<Orden> listarPorEstado(String estado);
    Mono<Orden> obtenerPorId(Integer id);
    Mono<Orden> insertar(Orden orden);
    Mono<Void> cambiarEstado(Integer idOrden, String estado);
    Mono<Void> actualizarTotal(Integer idOrden, Double total);
    Mono<Double> calcularTotalPorOrden(Integer idOrden);
}