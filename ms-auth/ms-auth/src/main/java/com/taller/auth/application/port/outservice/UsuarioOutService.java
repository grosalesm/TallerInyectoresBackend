package com.taller.auth.application.port.outservice;

import com.taller.auth.domain.bean.Rol;
import com.taller.auth.domain.bean.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioOutService {
    Flux<Usuario> listar();
    Flux<Rol> listarRoles();
    Mono<Usuario> obtenerPorId(Integer id);
    Mono<Usuario> obtenerPorNombreUsuario(String nombreUsuario);
    Mono<Usuario> insertar(Usuario usuario);
    Mono<Usuario> actualizar(Usuario usuario);
    Mono<Void> cambiarClave(Integer idUsuario, String claveHasheada);
    Mono<Void> eliminar(Integer id);
}