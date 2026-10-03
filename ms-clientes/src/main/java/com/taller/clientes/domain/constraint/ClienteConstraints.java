package com.taller.clientes.domain.constraint;

import com.taller.clientes.domain.bean.Cliente;

public interface ClienteConstraints {
    boolean validarDatos(Cliente cliente);
}