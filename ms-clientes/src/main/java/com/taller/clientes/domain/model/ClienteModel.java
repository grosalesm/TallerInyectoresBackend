package com.taller.clientes.domain.model;

import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.domain.constraint.ClienteConstraints;
import org.springframework.stereotype.Component;

@Component
public class ClienteModel implements ClienteConstraints {

    @Override
    public boolean validarDatos(Cliente c) {
        if (c == null) return false;
        if (c.getNombres() == null || c.getNombres().isBlank()) return false;
        if (c.getApellidos() == null || c.getApellidos().isBlank()) return false;
        if (c.getDni() == null || c.getDni().isBlank()) return false;
        if (c.getDni().length() != 8) return false;
        return true;
    }
}