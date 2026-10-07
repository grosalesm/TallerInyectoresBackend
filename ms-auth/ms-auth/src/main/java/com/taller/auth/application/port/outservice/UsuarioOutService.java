package com.taller.auth.application.port.outservice;

import com.taller.auth.domain.bean.Rol;
import com.taller.auth.domain.bean.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioOutService {
    List<Usuario> listar();
    List<Rol> listarRoles();
    Optional<Usuario> obtenerPorId(Integer id);
    Optional<Usuario> obtenerPorNombreUsuario(String nombreUsuario);
    Usuario insertar(Usuario usuario);
    Usuario actualizar(Usuario usuario);
    void cambiarClave(Integer idUsuario, String claveHasheada);
    void eliminar(Integer id);
}