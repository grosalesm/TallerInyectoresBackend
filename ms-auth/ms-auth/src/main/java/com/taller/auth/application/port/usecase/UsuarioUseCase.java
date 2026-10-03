package com.taller.auth.application.port.usecase;

import com.taller.auth.domain.bean.Rol;
import com.taller.auth.domain.bean.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioUseCase {
    Flux<Usuario> listar();
    Flux<Rol> listarRoles();
    Mono<Usuario> obtenerPorId(Integer id);
    Mono<Usuario> guardar(Usuario usuario);
    Mono<Void> cambiarClave(Integer idUsuario, String nuevaClave);
    Mono<Void> eliminar(Integer id);
}