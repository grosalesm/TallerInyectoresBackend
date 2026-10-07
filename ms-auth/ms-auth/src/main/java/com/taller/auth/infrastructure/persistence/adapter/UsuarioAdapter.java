package com.taller.auth.infrastructure.persistence.adapter;

import com.taller.auth.application.port.outservice.UsuarioOutService;
import com.taller.auth.domain.bean.Rol;
import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.infrastructure.mapper.RolMapper;
import com.taller.auth.infrastructure.mapper.UsuarioMapper;
import com.taller.auth.infrastructure.persistence.entity.RolEntity;
import com.taller.auth.infrastructure.persistence.entity.UsuarioEntity;
import com.taller.auth.infrastructure.persistence.repository.RolRepository;
import com.taller.auth.infrastructure.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioAdapter implements UsuarioOutService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final RolMapper rolMapper;

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::conRol)
                .toList();
    }

    @Override
    public List<Rol> listarRoles() {
        return rolRepository.findAll().stream()
                .map(rolMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).map(this::conRol);
    }

    @Override
    public Optional<Usuario> obtenerPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario).map(this::conRol);
    }

    @Override
    public Usuario insertar(Usuario usuario) {
        usuario.setIdUsuario(null);
        UsuarioEntity saved = usuarioRepository.save(usuarioMapper.toEntity(usuario));
        return conRol(saved);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        UsuarioEntity entity = usuarioRepository.findById(usuario.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado con id: " + usuario.getIdUsuario()));
        entity.setNombre(usuario.getNombre());
        entity.setNombreUsuario(usuario.getNombreUsuario());
        entity.setIdRol(usuario.getIdRol());
        if (usuario.getActivo() != null) {
            entity.setActivo(usuario.getActivo());
        }
        UsuarioEntity saved = usuarioRepository.save(entity);
        return conRol(saved);
    }

    @Override
    public void cambiarClave(Integer idUsuario, String claveHasheada) {
        UsuarioEntity entity = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado con id: " + idUsuario));
        entity.setClave(claveHasheada);
        usuarioRepository.save(entity);
    }

    @Override
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    private Usuario conRol(UsuarioEntity entity) {
        Usuario usuario = usuarioMapper.toDomain(entity);
        if (entity.getIdRol() == null) {
            return usuario;
        }
        rolRepository.findById(entity.getIdRol()).ifPresent(rol ->
                usuario.setNombreRol(rol.getNombre()));
        return usuario;
    }
}