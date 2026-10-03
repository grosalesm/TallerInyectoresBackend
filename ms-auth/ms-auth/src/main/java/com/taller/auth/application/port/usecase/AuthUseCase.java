package com.taller.auth.application.port.usecase;

import com.taller.auth.domain.bean.Usuario;
import reactor.core.publisher.Mono;

public interface AuthUseCase {
    Mono<Usuario> login(String nombreUsuario, String clave);
}