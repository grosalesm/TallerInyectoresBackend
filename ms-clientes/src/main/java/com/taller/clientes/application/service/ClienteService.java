package com.taller.clientes.application.service;

import com.taller.clientes.application.port.outservice.ClienteEventOutService;
import com.taller.clientes.application.port.outservice.ClienteOutService;
import com.taller.clientes.application.port.usecase.ClienteUseCase;
import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.domain.constraint.ClienteConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteUseCase {

    private final ClienteOutService clienteOutService;
    private final ClienteEventOutService clienteEventOutService;
    private final ClienteConstraints clienteConstraints;

    @Override
    public List<Cliente> listar() {
        return clienteOutService.listar();
    }

    @Override
    public Optional<Cliente> obtenerPorId(Integer id) {
        return clienteOutService.obtenerPorId(id);
    }

    @Override
    @Transactional
    public Cliente guardar(Cliente cliente) {
        if (!clienteConstraints.validarDatos(cliente)) {
            throw new IllegalArgumentException("Datos del cliente inválidos.");
        }

        boolean esNuevo = cliente.getIdCliente() == null || cliente.getIdCliente() == 0;

        Optional<Cliente> existenteOpt = clienteOutService.obtenerPorDni(cliente.getDni());

        if (existenteOpt.isPresent()) {
            Cliente existente = existenteOpt.get();
            if (esNuevo || !existente.getIdCliente().equals(cliente.getIdCliente())) {
                throw new IllegalArgumentException("El DNI ya está registrado.");
            }
            Cliente actualizado = clienteOutService.actualizar(cliente);
            clienteEventOutService.publicarClienteActualizado(actualizado);
            return actualizado;
        }

        if (esNuevo) {
            Cliente creado = clienteOutService.insertar(cliente);
            clienteEventOutService.publicarClienteCreado(creado);
            return creado;
        }

        Cliente actualizado = clienteOutService.actualizar(cliente);
        clienteEventOutService.publicarClienteActualizado(actualizado);
        return actualizado;
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        clienteOutService.eliminar(id);
        clienteEventOutService.publicarClienteEliminado(id);
    }
}