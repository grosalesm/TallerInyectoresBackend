package com.taller.catalogos.infrastructure.controller;

import com.taller.catalogos.application.port.usecase.ServicioUseCase;
import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.infrastructure.dto.ServicioRequest;
import com.taller.catalogos.infrastructure.dto.ServicioResponse;
import com.taller.catalogos.infrastructure.mapper.CatalogoWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/servicio")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioUseCase servicioUseCase;
    private final CatalogoWebMapper mapper;

    @GetMapping
    public Flux<ServicioResponse> listar() {
        return servicioUseCase.listar().map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ServicioResponse>> obtenerPorId(@PathVariable Integer id) {
        return servicioUseCase.obtenerPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ServicioResponse>> crear(@Valid @RequestBody ServicioRequest request) {
        Servicio servicio = mapper.toDomain(request);
        servicio.setIdServicio(0);
        return servicioUseCase.guardar(servicio)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ServicioResponse>> actualizar(@PathVariable Integer id,
                                                             @Valid @RequestBody ServicioRequest request) {
        Servicio servicio = mapper.toDomain(request);
        servicio.setIdServicio(id);
        return servicioUseCase.guardar(servicio)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable Integer id) {
        return servicioUseCase.eliminar(id)
                .thenReturn(ResponseEntity.noContent().build());
    }
}