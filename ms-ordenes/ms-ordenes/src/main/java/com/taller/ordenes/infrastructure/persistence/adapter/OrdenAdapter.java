package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.OrdenOutService;
import com.taller.ordenes.domain.bean.Orden;
import com.taller.ordenes.infrastructure.mapper.OrdenMapper;
import com.taller.ordenes.infrastructure.persistence.repository.OrdenR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrdenAdapter implements OrdenOutService {

    private final OrdenR2dbcRepository repository;
    private final OrdenMapper mapper;

    @Override
    public Flux<Orden> listar() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Flux<Orden> listarPorEstado(String estado) {
        return repository.findByEstado(estado).map(mapper::toDomain);
    }

    @Override
    public Mono<Orden> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Orden> insertar(Orden orden) {
        orden.setIdOrden(null);
        orden.setFechaIngreso(LocalDateTime.now());
        orden.setTotal(0.0);
        return repository.save(mapper.toEntity(orden)).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> cambiarEstado(Integer idOrden, String estado) {
        return repository.findById(idOrden)
                .flatMap(entity -> {
                    entity.setEstado(estado);
                    if ("Pagado".equalsIgnoreCase(estado)) {
                        entity.setFechaSalida(LocalDateTime.now());
                    }
                    return repository.save(entity);
                })
                .then();
    }

    @Override
    public Mono<Void> actualizarTotal(Integer idOrden, Double total) {
        return repository.findById(idOrden)
                .flatMap(entity -> {
                    entity.setTotal(total);
                    return repository.save(entity);
                })
                .then();
    }

    @Override
    public Mono<Double> calcularTotalPorOrden(Integer idOrden) {
        return repository.calcularTotalPorOrden(idOrden).defaultIfEmpty(0.0);
    }
}