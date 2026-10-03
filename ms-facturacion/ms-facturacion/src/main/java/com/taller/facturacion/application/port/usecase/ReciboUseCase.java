package com.taller.facturacion.application.port.usecase;

import com.taller.facturacion.domain.bean.Recibo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReciboUseCase {
    Flux<Recibo> listar();
    Flux<Recibo> listarPorMes(int mes, int anio);
    Mono<Recibo> obtenerPorId(Integer id);
    Mono<Recibo> obtenerPorOrden(Integer idOrden);
    Mono<Recibo> registrar(Integer idOrden, String metodoPago, String numOperacion);
}