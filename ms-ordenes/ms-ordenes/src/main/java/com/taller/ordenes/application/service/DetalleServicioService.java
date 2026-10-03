package com.taller.ordenes.application.service;

import com.taller.ordenes.application.port.outservice.CatalogoOutService;
import com.taller.ordenes.application.port.outservice.DetalleServicioOutService;
import com.taller.ordenes.application.port.outservice.OrdenOutService;
import com.taller.ordenes.application.port.usecase.DetalleServicioUseCase;
import com.taller.ordenes.domain.bean.DetalleServicio;
import com.taller.ordenes.domain.constraint.DetalleServicioConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DetalleServicioService implements DetalleServicioUseCase {

    private final DetalleServicioOutService detalleServicioOutService;
    private final OrdenOutService ordenOutService;
    private final CatalogoOutService catalogoOutService;
    private final DetalleServicioConstraints detalleServicioConstraints;

    @Override
    public Flux<DetalleServicio> listarPorOrden(Integer idOrden) {
        return detalleServicioOutService.listarPorOrden(idOrden)
                .flatMap(this::enriquecerConDatosCatalogo);
    }

    @Override
    public Mono<Void> agregar(Integer idOrden, DetalleServicio detalle) {
        if (!detalleServicioConstraints.validar(detalle)) {
            return Mono.error(new IllegalArgumentException("Datos del servicio inválidos."));
        }
        return catalogoOutService.existeServicio(detalle.getIdServicio())
                .flatMap(existe -> {
                    if (Boolean.FALSE.equals(existe)) {
                        return Mono.error(new IllegalArgumentException("El servicio no existe."));
                    }
                    detalle.setIdOrden(idOrden);
                    return detalleServicioOutService.guardar(detalle);
                })
                .then(recalcularTotal(idOrden));
    }

    @Override
    public Mono<Void> eliminar(Integer idDetalle) {
        return detalleServicioOutService.obtenerPorId(idDetalle)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Detalle no encontrado.")))
                .flatMap(detalle -> {
                    Integer idOrden = detalle.getIdOrden();
                    return detalleServicioOutService.eliminar(idDetalle)
                            .then(recalcularTotal(idOrden));
                });
    }

    private Mono<DetalleServicio> enriquecerConDatosCatalogo(DetalleServicio detalle) {
        return catalogoOutService.obtenerNombreServicio(detalle.getIdServicio())
                .defaultIfEmpty("")
                .map(nombre -> {
                    detalle.setNombreServicio(nombre);
                    return detalle;
                });
    }

    private Mono<Void> recalcularTotal(Integer idOrden) {
        return ordenOutService.calcularTotalPorOrden(idOrden)
                .flatMap(total -> ordenOutService.actualizarTotal(idOrden, total));
    }
}