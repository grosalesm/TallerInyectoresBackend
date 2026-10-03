package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.DetalleInyectorOutService;
import com.taller.ordenes.domain.bean.DetalleInyector;
import com.taller.ordenes.infrastructure.mapper.DetalleInyectorMapper;
import com.taller.ordenes.infrastructure.persistence.repository.DetalleInyectorR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DetalleInyectorAdapter implements DetalleInyectorOutService {

    private final DetalleInyectorR2dbcRepository repository;
    private final DetalleInyectorMapper mapper;

    @Override
    public Flux<DetalleInyector> listarPorOrden(Integer idOrden) {
        return repository.findByIdOrden(idOrden).map(mapper::toDomain);
    }

    @Override
    public Mono<DetalleInyector> guardar(DetalleInyector detalle) {
        detalle.setIdDetalle(null);
        return repository.save(mapper.toEntity(detalle)).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Integer idDetalle) {
        return repository.deleteById(idDetalle);
    }

    @Override
    public Mono<DetalleInyector> obtenerPorId(Integer idDetalle) {
        return repository.findById(idDetalle).map(mapper::toDomain);
    }
}