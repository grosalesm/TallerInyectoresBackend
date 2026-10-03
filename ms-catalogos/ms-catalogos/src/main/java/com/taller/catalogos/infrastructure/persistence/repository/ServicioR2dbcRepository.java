package com.taller.catalogos.infrastructure.persistence.repository;

import com.taller.catalogos.infrastructure.persistence.entity.ServicioEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ServicioR2dbcRepository extends ReactiveCrudRepository<ServicioEntity, Integer> {

    @Query("SELECT * FROM servicios WHERE activo = true")
    Flux<ServicioEntity> findActivos();
}