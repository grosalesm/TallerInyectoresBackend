package com.taller.catalogos.application.service;

import com.taller.catalogos.application.port.outservice.CatalogoEventOutService;
import com.taller.catalogos.application.port.outservice.ServicioOutService;
import com.taller.catalogos.application.port.usecase.ServicioUseCase;
import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.domain.constraint.ServicioConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ServicioService implements ServicioUseCase {

    private final ServicioOutService servicioOutService;
    private final ServicioConstraints servicioConstraints;
    private final CatalogoEventOutService catalogoEventOutService;

    @Override
    public Flux<Servicio> listar() {
        return servicioOutService.listar();
    }

    @Override
    public Flux<Servicio> listarActivos() {
        return servicioOutService.listarActivos();
    }

    @Override
    public Mono<Servicio> obtenerPorId(Integer id) {
        return servicioOutService.obtenerPorId(id);
    }

    @Override
    public Mono<Servicio> guardar(Servicio servicio) {
        if (!servicioConstraints.validar(servicio)) {
            return Mono.error(new IllegalArgumentException("El nombre es obligatorio y el precio debe ser mayor a cero."));
        }
        boolean esNuevo = servicio.getIdServicio() == null || servicio.getIdServicio() == 0;
        if (esNuevo) {
            servicio.setIdServicio(0);
            if (servicio.getActivo() == null) servicio.setActivo(true);
            return servicioOutService.insertar(servicio)
                    .doOnNext(catalogoEventOutService::publicarServicioActualizado);
        }
        return servicioOutService.actualizar(servicio)
                .doOnNext(catalogoEventOutService::publicarServicioActualizado);
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return servicioOutService.eliminar(id);
    }
}