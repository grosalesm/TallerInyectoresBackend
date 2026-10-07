package com.taller.clientes.application.port.outservice;

import com.taller.clientes.domain.bean.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteOutService {
    List<Cliente> listar();
    Optional<Cliente> obtenerPorId(Integer id);
    Optional<Cliente> obtenerPorDni(String dni);
    Cliente insertar(Cliente cliente);
    Cliente actualizar(Cliente cliente);
    void eliminar(Integer id);
}