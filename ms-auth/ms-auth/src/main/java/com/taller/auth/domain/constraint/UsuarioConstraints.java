package com.taller.auth.domain.constraint;

import com.taller.auth.domain.bean.Usuario;

public interface UsuarioConstraints {
    boolean validarCreacion(Usuario usuario);
    boolean validarActualizacion(Usuario usuario);
    boolean validarClave(String clave);
}