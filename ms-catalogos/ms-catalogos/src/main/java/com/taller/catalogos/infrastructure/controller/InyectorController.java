package com.taller.catalogos.infrastructure.controller;

import com.taller.catalogos.application.port.usecase.InyectorUseCase;
import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.infrastructure.dto.InyectorRequest;
import com.taller.catalogos.infrastructure.dto.InyectorResponse;
import com.taller.catalogos.infrastructure.mapper.CatalogoWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/inyector")
@RequiredArgsConstructor
public class InyectorController {

    private final InyectorUseCase inyectorUseCase;
    private final CatalogoWebMapper mapper;

    @GetMapping
    public Flux<InyectorResponse> listar() {
        return inyectorUseCase.listar().map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<InyectorResponse>> obtenerPorId(@PathVariable Integer id) {
        return inyectorUseCase.obtenerPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<InyectorResponse>> crear(@Valid @RequestBody InyectorRequest request) {
        Inyector inyector = mapper.toDomain(request);
        inyector.setIdInyector(0);
        return inyectorUseCase.guardar(inyector)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<InyectorResponse>> actualizar(@PathVariable Integer id,
                                                             @Valid @RequestBody InyectorRequest request) {
        Inyector inyector = mapper.toDomain(request);
        inyector.setIdInyector(id);
        return inyectorUseCase.guardar(inyector)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Integer id) {
        return inyectorUseCase.eliminar(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}