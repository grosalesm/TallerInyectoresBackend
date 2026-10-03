package com.taller.clientes.application.port.outservice;

import com.taller.clientes.domain.bean.Cliente;

public interface ClienteEventOutService {
    void publicarClienteCreado(Cliente cliente);
    void publicarClienteActualizado(Cliente cliente);
    void publicarClienteEliminado(Integer idCliente);
}