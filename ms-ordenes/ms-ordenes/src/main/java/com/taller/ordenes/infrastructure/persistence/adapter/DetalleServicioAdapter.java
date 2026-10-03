package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.DetalleServicioOutService;
import com.taller.ordenes.domain.bean.DetalleServicio;
import com.taller.ordenes.infrastructure.mapper.DetalleServicioMapper;
import com.taller.ordenes.infrastructure.persistence.repository.DetalleServicioR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DetalleServicioAdapter implements DetalleServicioOutService {

    private final DetalleServicioR2dbcRepository repository;
    private final DetalleServicioMapper mapper;

    @Override
    public Flux<DetalleServicio> listarPorOrden(Integer idOrden) {
        return repository.findByIdOrden(idOrden).map(mapper::toDomain);
    }

    @Override
    public Mono<DetalleServicio> guardar(DetalleServicio detalle) {
        detalle.setIdDetalle(null);
        return repository.save(mapper.toEntity(detalle)).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Integer idDetalle) {
        return repository.deleteById(idDetalle);
    }

    @Override
    public Mono<DetalleServicio> obtenerPorId(Integer idDetalle) {
        return repository.findById(idDetalle).map(mapper::toDomain);
    }
}