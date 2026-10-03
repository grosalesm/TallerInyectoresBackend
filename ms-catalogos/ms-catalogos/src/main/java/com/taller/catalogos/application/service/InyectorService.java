package com.taller.catalogos.application.service;

import com.taller.catalogos.application.port.outservice.CatalogoEventOutService;
import com.taller.catalogos.application.port.outservice.InyectorOutService;
import com.taller.catalogos.application.port.usecase.InyectorUseCase;
import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.domain.constraint.InyectorConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InyectorService implements InyectorUseCase {

    private final InyectorOutService inyectorOutService;
    private final InyectorConstraints inyectorConstraints;
    private final CatalogoEventOutService catalogoEventOutService;

    @Override
    public Flux<Inyector> listar() {
        return inyectorOutService.listar();
    }

    @Override
    public Flux<Inyector> listarActivos() {
        return inyectorOutService.listarActivos();
    }

    @Override
    public Mono<Inyector> obtenerPorId(Integer id) {
        return inyectorOutService.obtenerPorId(id);
    }

    @Override
    public Mono<Inyector> guardar(Inyector inyector) {
        if (!inyectorConstraints.validar(inyector)) {
            return Mono.error(new IllegalArgumentException("El modelo es obligatorio."));
        }
        boolean esNuevo = inyector.getIdInyector() == null || inyector.getIdInyector() == 0;
        if (esNuevo) {
            inyector.setIdInyector(0);
            if (inyector.getActivo() == null) inyector.setActivo(true);
            return inyectorOutService.insertar(inyector)
                    .doOnNext(catalogoEventOutService::publicarInyectorActualizado);
        }
        return inyectorOutService.actualizar(inyector)
                .doOnNext(catalogoEventOutService::publicarInyectorActualizado);
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return inyectorOutService.eliminar(id);
    }
}