package com.taller.ordenes.infrastructure.persistence.repository;

import com.taller.ordenes.infrastructure.persistence.entity.DetalleInyectorEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DetalleInyectorR2dbcRepository extends ReactiveCrudRepository<DetalleInyectorEntity, Integer> {
    Flux<DetalleInyectorEntity> findByIdOrden(Integer idOrden);
}