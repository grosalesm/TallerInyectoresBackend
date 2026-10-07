package com.taller.catalogos.infrastructure.controller;

import com.taller.catalogos.application.port.usecase.ServicioUseCase;
import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.infrastructure.dto.ServicioRequest;
import com.taller.catalogos.infrastructure.dto.ServicioResponse;
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
@RequestMapping("/api/servicio")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioUseCase servicioUseCase;
    private final CatalogoWebMapper mapper;

    @GetMapping
    public List<ServicioResponse> listar() {
        return servicioUseCase.listar().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponse> obtenerPorId(@PathVariable Integer id) {
        return servicioUseCase.obtenerPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServicioResponse> crear(@Valid @RequestBody ServicioRequest request) {
        Servicio servicio = mapper.toDomain(request);
        servicio.setIdServicio(null);
        Servicio creado = servicioUseCase.guardar(servicio);
        return ResponseEntity.ok(mapper.toResponse(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponse> actualizar(@PathVariable Integer id,
                                                       @Valid @RequestBody ServicioRequest request) {
        Servicio servicio = mapper.toDomain(request);
        servicio.setIdServicio(id);
        Servicio actualizado = servicioUseCase.guardar(servicio);
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}