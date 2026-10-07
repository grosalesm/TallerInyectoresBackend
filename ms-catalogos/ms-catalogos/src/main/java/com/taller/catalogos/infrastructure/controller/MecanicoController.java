package com.taller.catalogos.infrastructure.controller;

import com.taller.catalogos.application.port.usecase.MecanicoUseCase;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.infrastructure.dto.MecanicoRequest;
import com.taller.catalogos.infrastructure.dto.MecanicoResponse;
import com.taller.catalogos.infrastructure.mapper.CatalogoWebMapper;
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
@RequestMapping("/api/mecanico")
@RequiredArgsConstructor
public class MecanicoController {

    private final MecanicoUseCase mecanicoUseCase;
    private final CatalogoWebMapper mapper;

    @GetMapping
    public List<MecanicoResponse> listar() {
        return mecanicoUseCase.listar().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MecanicoResponse> obtenerPorId(@PathVariable Integer id) {
        return mecanicoUseCase.obtenerPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MecanicoResponse> crear(@Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanico = mapper.toDomain(request);
        mecanico.setIdMecanico(null);
        Mecanico creado = mecanicoUseCase.guardar(mecanico);
        return ResponseEntity.ok(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MecanicoResponse> actualizar(@PathVariable Integer id,
                                                       @Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanico = mapper.toDomain(request);
        mecanico.setIdMecanico(id);
        Mecanico actualizado = mecanicoUseCase.guardar(mecanico);
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        mecanicoUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}