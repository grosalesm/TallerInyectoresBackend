package com.taller.auth.infrastructure.controller;

import com.taller.auth.application.port.outservice.JwtOutService;
import com.taller.auth.application.port.usecase.AuthUseCase;
import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.infrastructure.dto.PeticionLogin;
import com.taller.auth.infrastructure.dto.RespuestaLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;
    private final JwtOutService jwtOutService;

    @PostMapping("/login")
    public ResponseEntity<RespuestaLogin> login(@Valid @RequestBody PeticionLogin request) {
        Usuario usuario = authUseCase.login(request.getNombreUsuario(), request.getClave());
        String token = jwtOutService.generarToken(usuario);
        return ResponseEntity.ok(new RespuestaLogin(
                token,
                usuario.getNombre(),
                usuario.getNombreRol(),
                usuario.getIdUsuario()
        ));
    }
}