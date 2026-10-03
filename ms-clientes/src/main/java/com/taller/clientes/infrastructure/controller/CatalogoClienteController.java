package com.taller.clientes.infrastructure.controller;

import com.taller.clientes.application.port.usecase.ClienteUseCase;
import com.taller.clientes.infrastructure.dto.ClienteResponse;
import com.taller.clientes.infrastructure.mapper.ClienteWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoClienteController {

    private final ClienteUseCase clienteUseCase;
    private final ClienteWebMapper clienteWebMapper;

    @GetMapping("/clientes")
    public Flux<ClienteResponse> clientes() {
        return clienteUseCase.listar().map(clienteWebMapper::toResponse);
    }
}