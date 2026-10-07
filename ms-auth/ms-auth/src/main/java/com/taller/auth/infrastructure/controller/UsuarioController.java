package com.taller.auth.infrastructure.controller;

import com.taller.auth.application.port.usecase.UsuarioUseCase;
import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.infrastructure.dto.PeticionClave;
import com.taller.auth.infrastructure.dto.RolResponse;
import com.taller.auth.infrastructure.dto.UsuarioRequest;
import com.taller.auth.infrastructure.dto.UsuarioResponse;
import com.taller.auth.infrastructure.mapper.UsuarioWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioWebMapper usuarioWebMapper;

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioUseCase.listar().stream()
                .map(usuarioWebMapper::toResponse)
                .toList();
    }

    @GetMapping("/roles")
    public List<RolResponse> listarRoles() {
        return usuarioUseCase.listarRoles().stream()
                .map(r -> new RolResponse(r.getIdRol(), r.getNombre()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Integer id) {
        return usuarioUseCase.obtenerPorId(id)
                .map(usuarioWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(null);
        usuario.setNombre(request.getNombre());
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setClave(request.getClave());
        usuario.setIdRol(request.getIdRol());
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : true);

        Usuario creado = usuarioUseCase.guardar(usuario);
        return ResponseEntity.ok(usuarioWebMapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Integer id,
                                                      @Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNombre(request.getNombre());
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setIdRol(request.getIdRol());
        usuario.setActivo(request.getActivo());

        Usuario actualizado = usuarioUseCase.guardar(usuario);
        return ResponseEntity.ok(usuarioWebMapper.toResponse(actualizado));
    }

    @PatchMapping("/{id}/clave")
    public ResponseEntity<Void> cambiarClave(@PathVariable Integer id,
                                             @Valid @RequestBody PeticionClave request) {
        usuarioUseCase.cambiarClave(id, request.getNuevaClave());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        usuarioUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}