package com.taller.auth.infrastructure.controller;

import com.taller.auth.application.port.outservice.JwtOutService;
import com.taller.auth.application.port.usecase.AuthUseCase;
import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.infrastructure.dto.PeticionLogin;
import com.taller.auth.infrastructure.dto.RespuestaLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;
    private final JwtOutService jwtOutService;

    @PostMapping("/login")
    public Mono<ResponseEntity<RespuestaLogin>> login(@Valid @RequestBody PeticionLogin request) {
        return authUseCase.login(request.getNombreUsuario(), request.getClave())
                .map(usuario -> {
                    String token = jwtOutService.generarToken(usuario);
                    return ResponseEntity.ok(new RespuestaLogin(
                            token,
                            usuario.getNombre(),
                            usuario.getNombreRol(),
                            usuario.getIdUsuario()
                    ));
                });
    }
}