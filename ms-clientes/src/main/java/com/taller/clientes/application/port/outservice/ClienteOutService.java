package com.taller.clientes.application.port.outservice;

import com.taller.clientes.domain.bean.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteOutService {
    Flux<Cliente> listar();
    Mono<Cliente> obtenerPorId(Integer id);
    Mono<Cliente> obtenerPorDni(String dni);
    Mono<Cliente> insertar(Cliente cliente);
    Mono<Cliente> actualizar(Cliente cliente);
    Mono<Void> eliminar(Integer id);
}