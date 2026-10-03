package com.taller.clientes.application.service;

import com.taller.clientes.application.port.outservice.ClienteEventOutService;
import com.taller.clientes.application.port.outservice.ClienteOutService;
import com.taller.clientes.application.port.usecase.ClienteUseCase;
import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.domain.constraint.ClienteConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteUseCase {

    private final ClienteOutService clienteOutService;
    private final ClienteEventOutService clienteEventOutService;
    private final ClienteConstraints clienteConstraints;

    @Override
    public Flux<Cliente> listar() {
        return clienteOutService.listar();
    }

    @Override
    public Mono<Cliente> obtenerPorId(Integer id) {
        return clienteOutService.obtenerPorId(id);
    }

    @Override
    public Mono<Cliente> guardar(Cliente cliente) {
        if (!clienteConstraints.validarDatos(cliente)) {
            return Mono.error(new IllegalArgumentException("Datos del cliente inválidos."));
        }

        boolean esNuevo = cliente.getIdCliente() == null || cliente.getIdCliente() == 0;

        return clienteOutService.obtenerPorDni(cliente.getDni())
                .flatMap(existente -> {
                    if (esNuevo || !existente.getIdCliente().equals(cliente.getIdCliente())) {
                        return Mono.error(new IllegalArgumentException("El DNI ya está registrado."));
                    }
                    return clienteOutService.actualizar(cliente)
                            .doOnNext(clienteEventOutService::publicarClienteActualizado);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    if (esNuevo) {
                        return clienteOutService.insertar(cliente)
                                .doOnNext(clienteEventOutService::publicarClienteCreado);
                    }
                    return clienteOutService.actualizar(cliente)
                            .doOnNext(clienteEventOutService::publicarClienteActualizado);
                }));
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return clienteOutService.eliminar(id)
                .doOnSuccess(v -> clienteEventOutService.publicarClienteEliminado(id));
    }
}