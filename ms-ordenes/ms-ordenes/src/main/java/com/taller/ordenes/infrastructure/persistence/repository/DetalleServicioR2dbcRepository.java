package com.taller.ordenes.infrastructure.persistence.repository;

import com.taller.ordenes.infrastructure.persistence.entity.DetalleServicioEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DetalleServicioR2dbcRepository extends ReactiveCrudRepository<DetalleServicioEntity, Integer> {
    Flux<DetalleServicioEntity> findByIdOrden(Integer idOrden);
}