package com.taller.catalogos.application.service;

import com.taller.catalogos.application.port.outservice.CatalogoEventOutService;
import com.taller.catalogos.application.port.outservice.MecanicoOutService;
import com.taller.catalogos.application.port.usecase.MecanicoUseCase;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.domain.constraint.MecanicoConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MecanicoService implements MecanicoUseCase {

    private final MecanicoOutService mecanicoOutService;
    private final MecanicoConstraints mecanicoConstraints;
    private final CatalogoEventOutService catalogoEventOutService;

    @Override
    public Flux<Mecanico> listar() {
        return mecanicoOutService.listar();
    }

    @Override
    public Flux<Mecanico> listarActivos() {
        return mecanicoOutService.listarActivos();
    }

    @Override
    public Mono<Mecanico> obtenerPorId(Integer id) {
        return mecanicoOutService.obtenerPorId(id);
    }

    @Override
    public Mono<Mecanico> guardar(Mecanico mecanico) {
        if (!mecanicoConstraints.validar(mecanico)) {
            return Mono.error(new IllegalArgumentException("El nombre y el apellido son obligatorios."));
        }
        boolean esNuevo = mecanico.getIdMecanico() == null || mecanico.getIdMecanico() == 0;
        if (esNuevo) {
            mecanico.setIdMecanico(0);
            if (mecanico.getActivo() == null) mecanico.setActivo(true);
            return mecanicoOutService.insertar(mecanico)
                    .doOnNext(catalogoEventOutService::publicarMecanicoActualizado);
        }
        return mecanicoOutService.actualizar(mecanico)
                .doOnNext(catalogoEventOutService::publicarMecanicoActualizado);
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return mecanicoOutService.eliminar(id);
    }
}