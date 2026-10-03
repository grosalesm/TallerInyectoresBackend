package com.taller.facturacion.application.port.outservice;

import com.taller.facturacion.domain.bean.Recibo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReciboOutService {
    Flux<Recibo> listar();
    Flux<Recibo> listarPorMes(int mes, int anio);
    Mono<Recibo> obtenerPorId(Integer id);
    Mono<Recibo> obtenerPorOrden(Integer idOrden);
    Mono<Recibo> insertar(Recibo recibo);
    Mono<Long> contarRecibos();
}