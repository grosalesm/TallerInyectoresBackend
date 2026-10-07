package com.taller.auth.application.service;

import com.taller.auth.application.port.outservice.PasswordEncoderOutService;
import com.taller.auth.application.port.outservice.UsuarioOutService;
import com.taller.auth.application.port.usecase.AuthUseCase;
import com.taller.auth.domain.bean.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UsuarioOutService usuarioOutService;
    private final PasswordEncoderOutService passwordEncoder;

    @Override
    public Usuario login(String nombreUsuario, String clave) {
        if (nombreUsuario == null || nombreUsuario.isBlank()
                || clave == null || clave.isBlank()) {
            throw new IllegalArgumentException("Usuario o clave incorrectos.");
        }

        Usuario usuario = usuarioOutService.obtenerPorNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario o clave incorrectos."));

        if (Boolean.FALSE.equals(usuario.getActivo())) {
            throw new IllegalArgumentException("El usuario está inactivo.");
        }
        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new IllegalArgumentException("Usuario o clave incorrectos.");
        }
        usuario.setClave(null);
        return usuario;
    }
}