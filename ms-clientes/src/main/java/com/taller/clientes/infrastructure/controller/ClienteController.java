package com.taller.clientes.infrastructure.controller;

import com.taller.clientes.application.port.usecase.ClienteUseCase;
import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.infrastructure.dto.ClienteRequest;
import com.taller.clientes.infrastructure.dto.ClienteResponse;
import com.taller.clientes.infrastructure.mapper.ClienteWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;
    private final ClienteWebMapper clienteWebMapper;

    @GetMapping
    public Flux<ClienteResponse> listar() {
        return clienteUseCase.listar().map(clienteWebMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClienteResponse>> obtenerPorId(@PathVariable Integer id) {
        return clienteUseCase.obtenerPorId(id)
                .map(clienteWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ClienteResponse>> crear(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteWebMapper.toDomain(request);
        cliente.setIdCliente(0);
        return clienteUseCase.guardar(cliente)
                .map(clienteWebMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClienteResponse>> actualizar(@PathVariable Integer id,
                                                            @Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteWebMapper.toDomain(request);
        cliente.setIdCliente(id);
        return clienteUseCase.guardar(cliente)
                .map(clienteWebMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Integer id) {
        return clienteUseCase.eliminar(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}