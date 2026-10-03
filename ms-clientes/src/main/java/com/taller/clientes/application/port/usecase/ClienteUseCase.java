package com.taller.clientes.application.port.usecase;

import com.taller.clientes.domain.bean.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteUseCase {
    Flux<Cliente> listar();
    Mono<Cliente> obtenerPorId(Integer id);
    Mono<Cliente> guardar(Cliente cliente);
    Mono<Void> eliminar(Integer id);
}