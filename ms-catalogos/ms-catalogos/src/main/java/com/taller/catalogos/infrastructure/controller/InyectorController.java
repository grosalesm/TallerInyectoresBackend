package com.taller.catalogos.infrastructure.controller;

import com.taller.catalogos.application.port.usecase.InyectorUseCase;
import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.infrastructure.dto.InyectorRequest;
import com.taller.catalogos.infrastructure.dto.InyectorResponse;
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
@RequestMapping("/api/inyector")
@RequiredArgsConstructor
public class InyectorController {

    private final InyectorUseCase inyectorUseCase;
    private final CatalogoWebMapper mapper;

    @GetMapping
    public List<InyectorResponse> listar() {
        return inyectorUseCase.listar().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InyectorResponse> obtenerPorId(@PathVariable Integer id) {
        return inyectorUseCase.obtenerPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InyectorResponse> crear(@Valid @RequestBody InyectorRequest request) {
        Inyector inyector = mapper.toDomain(request);
        inyector.setIdInyector(null);
        Inyector creado = inyectorUseCase.guardar(inyector);
        return ResponseEntity.ok(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InyectorResponse> actualizar(@PathVariable Integer id,
                                                       @Valid @RequestBody InyectorRequest request) {
        Inyector inyector = mapper.toDomain(request);
        inyector.setIdInyector(id);
        Inyector actualizado = inyectorUseCase.guardar(inyector);
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inyectorUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}