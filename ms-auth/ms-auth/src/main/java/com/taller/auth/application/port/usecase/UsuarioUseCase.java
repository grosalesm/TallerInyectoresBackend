package com.taller.auth.application.port.usecase;

import com.taller.auth.domain.bean.Rol;
import com.taller.auth.domain.bean.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioUseCase {
    List<Usuario> listar();
    List<Rol> listarRoles();
    Optional<Usuario> obtenerPorId(Integer id);
    Usuario guardar(Usuario usuario);
    void cambiarClave(Integer idUsuario, String nuevaClave);
    void eliminar(Integer id);
}