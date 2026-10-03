package com.taller.ordenes.application.service;

import com.taller.ordenes.application.port.outservice.CatalogoOutService;
import com.taller.ordenes.application.port.outservice.DetalleInyectorOutService;
import com.taller.ordenes.application.port.usecase.DetalleInyectorUseCase;
import com.taller.ordenes.domain.bean.DetalleInyector;
import com.taller.ordenes.domain.constraint.DetalleInyectorConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DetalleInyectorService implements DetalleInyectorUseCase {

    private final DetalleInyectorOutService detalleInyectorOutService;
    private final CatalogoOutService catalogoOutService;
    private final DetalleInyectorConstraints detalleInyectorConstraints;

    @Override
    public Flux<DetalleInyector> listarPorOrden(Integer idOrden) {
        return detalleInyectorOutService.listarPorOrden(idOrden)
                .flatMap(this::enriquecerConDatosCatalogo);
    }

    @Override
    public Mono<Void> agregar(Integer idOrden, DetalleInyector detalle) {
        if (!detalleInyectorConstraints.validar(detalle)) {
            return Mono.error(new IllegalArgumentException("Datos del inyector inválidos."));
        }
        return catalogoOutService.existeInyector(detalle.getIdInyector())
                .flatMap(existe -> {
                    if (Boolean.FALSE.equals(existe)) {
                        return Mono.error(new IllegalArgumentException("El inyector no existe."));
                    }
                    detalle.setIdOrden(idOrden);
                    return detalleInyectorOutService.guardar(detalle);
                })
                .then();
    }

    @Override
    public Mono<Void> eliminar(Integer idDetalle) {
        return detalleInyectorOutService.eliminar(idDetalle);
    }

    private Mono<DetalleInyector> enriquecerConDatosCatalogo(DetalleInyector detalle) {
        Mono<String> modelo = catalogoOutService.obtenerModeloInyector(detalle.getIdInyector()).defaultIfEmpty("");
        Mono<String> marca = catalogoOutService.obtenerMarcaInyector(detalle.getIdInyector()).defaultIfEmpty("");
        return Mono.zip(modelo, marca).map(tuple -> {
            detalle.setModeloInyector(tuple.getT1());
            detalle.setMarcaInyector(tuple.getT2());
            return detalle;
        });
    }
}