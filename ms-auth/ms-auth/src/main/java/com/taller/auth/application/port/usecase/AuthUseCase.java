package com.taller.auth.application.port.usecase;

import com.taller.auth.domain.bean.Usuario;

public interface AuthUseCase {
    Usuario login(String nombreUsuario, String clave);
}