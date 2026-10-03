package com.taller.auth.infrastructure.persistence.adapter;

import com.taller.auth.application.port.outservice.UsuarioOutService;
import com.taller.auth.domain.bean.Rol;
import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.infrastructure.mapper.RolMapper;
import com.taller.auth.infrastructure.mapper.UsuarioMapper;
import com.taller.auth.infrastructure.persistence.repository.RolR2dbcRepository;
import com.taller.auth.infrastructure.persistence.repository.UsuarioR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UsuarioAdapter implements UsuarioOutService {

    private final UsuarioR2dbcRepository usuarioRepository;
    private final RolR2dbcRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final RolMapper rolMapper;

    @Override
    public Flux<Usuario> listar() {
        return usuarioRepository.findAll()
                .flatMap(this::conRol);
    }

    @Override
    public Flux<Rol> listarRoles() {
        return rolRepository.findAll().map(rolMapper::toDomain);
    }

    @Override
    public Mono<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).flatMap(this::conRol);
    }

    @Override
    public Mono<Usuario> obtenerPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario).flatMap(this::conRol);
    }

    @Override
    public Mono<Usuario> insertar(Usuario usuario) {
        usuario.setIdUsuario(null);
        return usuarioRepository.save(usuarioMapper.toEntity(usuario))
                .flatMap(this::conRol);
    }

    @Override
    public Mono<Usuario> actualizar(Usuario usuario) {
        return usuarioRepository.findById(usuario.getIdUsuario())
                .flatMap(entity -> {
                    entity.setNombre(usuario.getNombre());
                    entity.setNombreUsuario(usuario.getNombreUsuario());
                    entity.setIdRol(usuario.getIdRol());
                    if (usuario.getActivo() != null) {
                        entity.setActivo(usuario.getActivo());
                    }
                    return usuarioRepository.save(entity);
                })
                .flatMap(this::conRol);
    }

    @Override
    public Mono<Void> cambiarClave(Integer idUsuario, String claveHasheada) {
        return usuarioRepository.findById(idUsuario)
                .flatMap(entity -> {
                    entity.setClave(claveHasheada);
                    return usuarioRepository.save(entity);
                })
                .then();
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return usuarioRepository.deleteById(id);
    }

    private Mono<Usuario> conRol(com.taller.auth.infrastructure.persistence.entity.UsuarioEntity entity) {
        Usuario usuario = usuarioMapper.toDomain(entity);
        if (entity.getIdRol() == null) {
            return Mono.just(usuario);
        }
        return rolRepository.findById(entity.getIdRol())
                .map(rol -> {
                    usuario.setNombreRol(rol.getNombre());
                    return usuario;
                })
                .defaultIfEmpty(usuario);
    }
}