package com.taller.auth.application.service;

import com.taller.auth.application.port.outservice.PasswordEncoderOutService;
import com.taller.auth.application.port.outservice.UsuarioOutService;
import com.taller.auth.application.port.usecase.UsuarioUseCase;
import com.taller.auth.domain.bean.Rol;
import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.domain.constraint.UsuarioConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioUseCase {

    private final UsuarioOutService usuarioOutService;
    private final PasswordEncoderOutService passwordEncoder;
    private final UsuarioConstraints usuarioConstraints;

    @Override
    public Flux<Usuario> listar() {
        return usuarioOutService.listar()
                .doOnNext(u -> u.setClave(null));
    }

    @Override
    public Flux<Rol> listarRoles() {
        return usuarioOutService.listarRoles();
    }

    @Override
    public Mono<Usuario> obtenerPorId(Integer id) {
        return usuarioOutService.obtenerPorId(id)
                .doOnNext(u -> u.setClave(null));
    }

    @Override
    public Mono<Usuario> guardar(Usuario usuario) {
        boolean esNuevo = usuario.getIdUsuario() == null || usuario.getIdUsuario() == 0;

        if (esNuevo) {
            if (!usuarioConstraints.validarCreacion(usuario)) {
                return Mono.error(new IllegalArgumentException(
                        "Datos inválidos. Nombre, nombre de usuario, clave (mínimo 4 caracteres) y rol son obligatorios."));
            }
            return usuarioOutService.obtenerPorNombreUsuario(usuario.getNombreUsuario())
                    .flatMap(existente -> Mono.error(
                            new IllegalArgumentException("El nombre de usuario ya existe.")))
                    .switchIfEmpty(Mono.defer(() -> {
                        usuario.setIdUsuario(null);
                        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
                        if (usuario.getActivo() == null) usuario.setActivo(true);
                        return usuarioOutService.insertar(usuario);
                    }))
                    .cast(Usuario.class)
                    .doOnNext(u -> u.setClave(null));
        } else {
            if (!usuarioConstraints.validarActualizacion(usuario)) {
                return Mono.error(new IllegalArgumentException(
                        "Datos inválidos. Nombre, nombre de usuario y rol son obligatorios."));
            }
            return usuarioOutService.obtenerPorId(usuario.getIdUsuario())
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario no encontrado.")))
                    .flatMap(existente -> usuarioOutService.obtenerPorNombreUsuario(usuario.getNombreUsuario())
                            .flatMap(otro -> {
                                if (!otro.getIdUsuario().equals(usuario.getIdUsuario())) {
                                    return Mono.error(new IllegalArgumentException(
                                            "El nombre de usuario ya existe."));
                                }
                                return usuarioOutService.actualizar(usuario);
                            })
                            .switchIfEmpty(Mono.defer(() -> usuarioOutService.actualizar(usuario))))
                    .cast(Usuario.class)
                    .doOnNext(u -> u.setClave(null));
        }
    }

    @Override
    public Mono<Void> cambiarClave(Integer idUsuario, String nuevaClave) {
        if (!usuarioConstraints.validarClave(nuevaClave)) {
            return Mono.error(new IllegalArgumentException(
                    "La nueva clave es obligatoria y debe tener al menos 4 caracteres."));
        }
        return usuarioOutService.obtenerPorId(idUsuario)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario no encontrado.")))
                .flatMap(u -> usuarioOutService.cambiarClave(idUsuario, passwordEncoder.encode(nuevaClave)));
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return usuarioOutService.obtenerPorId(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario no encontrado.")))
                .flatMap(u -> usuarioOutService.eliminar(id));
    }
}