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
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioWebMapper usuarioWebMapper;

    @GetMapping
    public Flux<UsuarioResponse> listar() {
        return usuarioUseCase.listar().map(usuarioWebMapper::toResponse);
    }

    @GetMapping("/roles")
    public Flux<RolResponse> listarRoles() {
        return usuarioUseCase.listarRoles()
                .map(r -> new RolResponse(r.getIdRol(), r.getNombre()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UsuarioResponse>> obtenerPorId(@PathVariable Integer id) {
        return usuarioUseCase.obtenerPorId(id)
                .map(usuarioWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UsuarioResponse>> crear(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(0);
        usuario.setNombre(request.getNombre());
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setClave(request.getClave());
        usuario.setIdRol(request.getIdRol());
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : true);

        return usuarioUseCase.guardar(usuario)
                .map(usuarioWebMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UsuarioResponse>> actualizar(@PathVariable Integer id,
                                                            @Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNombre(request.getNombre());
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setIdRol(request.getIdRol());
        usuario.setActivo(request.getActivo());

        return usuarioUseCase.guardar(usuario)
                .map(usuarioWebMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PatchMapping("/{id}/clave")
    public Mono<ResponseEntity<Void>> cambiarClave(@PathVariable Integer id,
                                                   @Valid @RequestBody PeticionClave request) {
        return usuarioUseCase.cambiarClave(id, request.getNuevaClave())
                .thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Integer id) {
        return usuarioUseCase.eliminar(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}