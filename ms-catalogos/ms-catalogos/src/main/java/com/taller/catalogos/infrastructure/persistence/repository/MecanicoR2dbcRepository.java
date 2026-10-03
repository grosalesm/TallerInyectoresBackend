package com.taller.catalogos.infrastructure.persistence.repository;

import com.taller.catalogos.infrastructure.persistence.entity.MecanicoEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MecanicoR2dbcRepository extends ReactiveCrudRepository<MecanicoEntity, Integer> {

    @Query("SELECT * FROM mecanicos WHERE activo = true")
    Flux<MecanicoEntity> findActivos();
}