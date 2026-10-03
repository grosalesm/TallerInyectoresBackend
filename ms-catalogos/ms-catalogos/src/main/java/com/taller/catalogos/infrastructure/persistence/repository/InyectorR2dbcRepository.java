package com.taller.catalogos.infrastructure.persistence.repository;

import com.taller.catalogos.infrastructure.persistence.entity.InyectorEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface InyectorR2dbcRepository extends ReactiveCrudRepository<InyectorEntity, Integer> {

    @Query("SELECT * FROM inyectores WHERE activo = true")
    Flux<InyectorEntity> findActivos();
}