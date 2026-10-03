package com.taller.ordenes.application.service;

import com.taller.ordenes.application.port.outservice.ClienteOutService;
import com.taller.ordenes.application.port.outservice.MecanicoOutService;
import com.taller.ordenes.application.port.outservice.OrdenEventOutService;
import com.taller.ordenes.application.port.outservice.OrdenOutService;
import com.taller.ordenes.application.port.usecase.OrdenUseCase;
import com.taller.ordenes.domain.bean.Orden;
import com.taller.ordenes.domain.constraint.OrdenConstraints;
import com.taller.ordenes.domain.enums.EstadoOrden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrdenService implements OrdenUseCase {

    private final OrdenOutService ordenOutService;
    private final ClienteOutService clienteOutService;
    private final MecanicoOutService mecanicoOutService;
    private final OrdenEventOutService ordenEventOutService;
    private final OrdenConstraints ordenConstraints;

    @Override
    public Flux<Orden> listar() {
        return ordenOutService.listar()
                .flatMap(this::enriquecerConNombres);
    }

    @Override
    public Flux<Orden> listarPorEstado(String estado) {
        return ordenOutService.listarPorEstado(estado)
                .flatMap(this::enriquecerConNombres);
    }

    @Override
    public Mono<Orden> obtenerPorId(Integer id) {
        return ordenOutService.obtenerPorId(id)
                .flatMap(this::enriquecerConNombres);
    }

    @Override
    public Mono<Orden> crear(Orden orden) {
        if (!ordenConstraints.validarCreacion(orden)) {
            return Mono.error(new IllegalArgumentException("Debe seleccionar un cliente y un mecánico válidos."));
        }

        return clienteOutService.existeCliente(orden.getIdCliente())
                .flatMap(existeCliente -> {
                    if (Boolean.FALSE.equals(existeCliente)) {
                        return Mono.error(new IllegalArgumentException("El cliente no existe."));
                    }
                    return mecanicoOutService.existeMecanico(orden.getIdMecanico());
                })
                .flatMap(existeMecanico -> {
                    if (Boolean.FALSE.equals(existeMecanico)) {
                        return Mono.error(new IllegalArgumentException("El mecánico no existe."));
                    }
                    orden.setEstado(EstadoOrden.PENDIENTE.getDescripcion());
                    return ordenOutService.insertar(orden);
                })
                .doOnNext(ordenEventOutService::publicarOrdenCreada)
                .flatMap(this::enriquecerConNombres);
    }

    @Override
    public Mono<Void> cambiarEstado(Integer idOrden, String estado) {
        if (!ordenConstraints.validarEstadoManual(estado)) {
            return Mono.error(new IllegalArgumentException(
                    "Estado no válido. Solo se permite 'Pendiente', 'En Proceso' o 'Terminado' manualmente."));
        }
        return ordenOutService.obtenerPorId(idOrden)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Orden no encontrada.")))
                .flatMap(orden -> {
                    String anterior = orden.getEstado();
                    return ordenOutService.cambiarEstado(idOrden, estado)
                            .doOnSuccess(v -> ordenEventOutService.publicarEstadoCambiado(idOrden, anterior, estado));
                });
    }

    @Override
    public Mono<Void> cambiarEstadoPorEvento(Integer idOrden, String estado) {
        if (!ordenConstraints.validarEstadoPorEvento(estado)) {
            return Mono.error(new IllegalArgumentException("Estado no válido."));
        }
        return ordenOutService.obtenerPorId(idOrden)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Orden no encontrada.")))
                .flatMap(orden -> {
                    String anterior = orden.getEstado();
                    if (anterior != null && anterior.equalsIgnoreCase(estado)) {
                        return Mono.empty();
                    }
                    return ordenOutService.cambiarEstado(idOrden, estado)
                            .doOnSuccess(v -> ordenEventOutService.publicarEstadoCambiado(idOrden, anterior, estado));
                });
    }

    private Mono<Orden> enriquecerConNombres(Orden orden) {
        Mono<String> nombreClienteMono = clienteOutService.obtenerNombreCliente(orden.getIdCliente())
                .defaultIfEmpty("");
        Mono<String> nombreMecanicoMono = mecanicoOutService.obtenerNombreMecanico(orden.getIdMecanico())
                .defaultIfEmpty("");

        return Mono.zip(nombreClienteMono, nombreMecanicoMono)
                .map(tuple -> {
                    orden.setNombreCliente(tuple.getT1());
                    orden.setNombreMecanico(tuple.getT2());
                    return orden;
                });
    }
}