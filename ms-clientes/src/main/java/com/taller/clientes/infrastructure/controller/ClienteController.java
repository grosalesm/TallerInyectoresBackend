package com.taller.clientes.infrastructure.controller;

import com.taller.clientes.application.port.usecase.ClienteUseCase;
import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.infrastructure.dto.ClienteRequest;
import com.taller.clientes.infrastructure.dto.ClienteResponse;
import com.taller.clientes.infrastructure.mapper.ClienteWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;
    private final ClienteWebMapper clienteWebMapper;

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteUseCase.listar().stream()
                .map(clienteWebMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(@PathVariable Integer id) {
        return clienteUseCase.obtenerPorId(id)
                .map(clienteWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteWebMapper.toDomain(request);
        cliente.setIdCliente(null); // null en vez de 0 para que JPA haga INSERT
        Cliente creado = clienteUseCase.guardar(cliente);
        return ResponseEntity.ok(clienteWebMapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(@PathVariable Integer id,
                                                      @Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteWebMapper.toDomain(request);
        cliente.setIdCliente(id);
        Cliente actualizado = clienteUseCase.guardar(cliente);
        return ResponseEntity.ok(clienteWebMapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}