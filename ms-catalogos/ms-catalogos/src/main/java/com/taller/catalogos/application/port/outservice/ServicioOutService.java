package com.taller.catalogos.application.port.outservice;

import com.taller.catalogos.domain.bean.Servicio;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioOutService {
    Flux<Servicio> listar();
    Flux<Servicio> listarActivos();
    Mono<Servicio> obtenerPorId(Integer id);
    Mono<Servicio> insertar(Servicio servicio);
    Mono<Servicio> actualizar(Servicio servicio);
    Mono<Void> eliminar(Integer id);
}