package com.taller.catalogos.infrastructure.controller;

import com.taller.catalogos.application.port.usecase.MecanicoUseCase;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.infrastructure.dto.MecanicoRequest;
import com.taller.catalogos.infrastructure.dto.MecanicoResponse;
import com.taller.catalogos.infrastructure.mapper.CatalogoWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/mecanico")
@RequiredArgsConstructor
public class MecanicoController {

    private final MecanicoUseCase mecanicoUseCase;
    private final CatalogoWebMapper mapper;

    @GetMapping
    public Flux<MecanicoResponse> listar() {
        return mecanicoUseCase.listar().map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MecanicoResponse>> obtenerPorId(@PathVariable Integer id) {
        return mecanicoUseCase.obtenerPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<MecanicoResponse>> crear(@Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanico = mapper.toDomain(request);
        mecanico.setIdMecanico(0);
        return mecanicoUseCase.guardar(mecanico)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MecanicoResponse>> actualizar(@PathVariable Integer id,
                                                             @Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanico = mapper.toDomain(request);
        mecanico.setIdMecanico(id);
        return mecanicoUseCase.guardar(mecanico)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Integer id) {
        return mecanicoUseCase.eliminar(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}