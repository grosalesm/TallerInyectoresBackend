package com.taller.catalogos.infrastructure.controller;

import com.taller.catalogos.application.port.usecase.InyectorUseCase;
import com.taller.catalogos.application.port.usecase.MecanicoUseCase;
import com.taller.catalogos.application.port.usecase.ServicioUseCase;
import com.taller.catalogos.infrastructure.dto.InyectorResponse;
import com.taller.catalogos.infrastructure.dto.MecanicoResponse;
import com.taller.catalogos.infrastructure.dto.ServicioResponse;
import com.taller.catalogos.infrastructure.mapper.CatalogoWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoController {

    private final InyectorUseCase inyectorUseCase;
    private final ServicioUseCase servicioUseCase;
    private final MecanicoUseCase mecanicoUseCase;
    private final CatalogoWebMapper mapper;

    @GetMapping("/inyectores")
    public Flux<InyectorResponse> inyectores() {
        return inyectorUseCase.listarActivos().map(mapper::toResponse);
    }

    @GetMapping("/servicios")
    public Flux<ServicioResponse> servicios() {
        return servicioUseCase.listarActivos().map(mapper::toResponse);
    }

    @GetMapping("/mecanicos")
    public Flux<MecanicoResponse> mecanicos() {
        return mecanicoUseCase.listarActivos().map(mapper::toResponse);
    }
}