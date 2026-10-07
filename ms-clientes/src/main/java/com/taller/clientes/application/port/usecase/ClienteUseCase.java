package com.taller.clientes.application.port.usecase;

import com.taller.clientes.domain.bean.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteUseCase {
    List<Cliente> listar();
    Optional<Cliente> obtenerPorId(Integer id);
    Cliente guardar(Cliente cliente);
    void eliminar(Integer id);
}