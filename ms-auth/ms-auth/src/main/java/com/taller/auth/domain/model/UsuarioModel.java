package com.taller.auth.domain.model;

import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.domain.constraint.UsuarioConstraints;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModel implements UsuarioConstraints {

    @Override
    public boolean validarCreacion(Usuario u) {
        if (u == null) return false;
        if (u.getNombre() == null || u.getNombre().isBlank()) return false;
        if (u.getNombreUsuario() == null || u.getNombreUsuario().isBlank()) return false;
        if (u.getClave() == null || u.getClave().isBlank()) return false;
        if (u.getClave().length() < 4) return false;
        if (u.getIdRol() == null || u.getIdRol() <= 0) return false;
        return true;
    }

    @Override
    public boolean validarActualizacion(Usuario u) {
        if (u == null) return false;
        if (u.getNombre() == null || u.getNombre().isBlank()) return false;
        if (u.getNombreUsuario() == null || u.getNombreUsuario().isBlank()) return false;
        if (u.getIdRol() == null || u.getIdRol() <= 0) return false;
        return true;
    }

    @Override
    public boolean validarClave(String clave) {
        if (clave == null || clave.isBlank()) return false;
        return clave.length() >= 4;
    }
}