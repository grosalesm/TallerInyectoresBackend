package com.taller.auth.application.service;

import com.taller.auth.application.port.outservice.PasswordEncoderOutService;
import com.taller.auth.application.port.outservice.UsuarioOutService;
import com.taller.auth.application.port.usecase.UsuarioUseCase;
import com.taller.auth.domain.bean.Rol;
import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.domain.constraint.UsuarioConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioUseCase {

    private final UsuarioOutService usuarioOutService;
    private final PasswordEncoderOutService passwordEncoder;
    private final UsuarioConstraints usuarioConstraints;

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = usuarioOutService.listar();
        usuarios.forEach(u -> u.setClave(null));
        return usuarios;
    }

    @Override
    public List<Rol> listarRoles() {
        return usuarioOutService.listarRoles();
    }

    @Override
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioOutService.obtenerPorId(id)
                .map(u -> {
                    u.setClave(null);
                    return u;
                });
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        boolean esNuevo = usuario.getIdUsuario() == null || usuario.getIdUsuario() == 0;

        if (esNuevo) {
            if (!usuarioConstraints.validarCreacion(usuario)) {
                throw new IllegalArgumentException(
                        "Datos inválidos. Nombre, nombre de usuario, clave (mínimo 4 caracteres) y rol son obligatorios.");
            }
            if (usuarioOutService.obtenerPorNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
                throw new IllegalArgumentException("El nombre de usuario ya existe.");
            }
            usuario.setIdUsuario(null);
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
            if (usuario.getActivo() == null) usuario.setActivo(true);
            Usuario creado = usuarioOutService.insertar(usuario);
            creado.setClave(null);
            return creado;
        }

        if (!usuarioConstraints.validarActualizacion(usuario)) {
            throw new IllegalArgumentException(
                    "Datos inválidos. Nombre, nombre de usuario y rol son obligatorios.");
        }
        usuarioOutService.obtenerPorId(usuario.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Optional<Usuario> porNombre = usuarioOutService.obtenerPorNombreUsuario(usuario.getNombreUsuario());
        if (porNombre.isPresent()
                && !porNombre.get().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe.");
        }

        Usuario actualizado = usuarioOutService.actualizar(usuario);
        actualizado.setClave(null);
        return actualizado;
    }

    @Override
    @Transactional
    public void cambiarClave(Integer idUsuario, String nuevaClave) {
        if (!usuarioConstraints.validarClave(nuevaClave)) {
            throw new IllegalArgumentException(
                    "La nueva clave es obligatoria y debe tener al menos 4 caracteres.");
        }
        usuarioOutService.obtenerPorId(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        usuarioOutService.cambiarClave(idUsuario, passwordEncoder.encode(nuevaClave));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        usuarioOutService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        usuarioOutService.eliminar(id);
    }
}