package com.taller.auth.application.service;

import com.taller.auth.application.port.outservice.JwtOutService;
import com.taller.auth.application.port.outservice.PasswordEncoderOutService;
import com.taller.auth.application.port.outservice.UsuarioOutService;
import com.taller.auth.application.port.usecase.AuthUseCase;
import com.taller.auth.domain.bean.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UsuarioOutService usuarioOutService;
    private final PasswordEncoderOutService passwordEncoder;
    private final JwtOutService jwtOutService;

    @Override
    public Mono<Usuario> login(String nombreUsuario, String clave) {
        if (nombreUsuario == null || nombreUsuario.isBlank() ||
                clave == null || clave.isBlank()) {
            return Mono.error(new IllegalArgumentException("Usuario o clave incorrectos."));
        }

        return usuarioOutService.obtenerPorNombreUsuario(nombreUsuario)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario o clave incorrectos.")))
                .flatMap(usuario -> {
                    if (Boolean.FALSE.equals(usuario.getActivo())) {
                        return Mono.error(new IllegalArgumentException("El usuario está inactivo."));
                    }
                    if (!passwordEncoder.matches(clave, usuario.getClave())) {
                        return Mono.error(new IllegalArgumentException("Usuario o clave incorrectos."));
                    }
                    return Mono.just(usuario);
                })
                .doOnNext(usuario -> usuario.setClave(null)); // no exponer el hash
    }
}