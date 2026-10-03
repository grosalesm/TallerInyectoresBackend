package com.taller.ordenes.infrastructure.persistence.repository;

import com.taller.ordenes.infrastructure.persistence.entity.OrdenEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrdenR2dbcRepository extends ReactiveCrudRepository<OrdenEntity, Integer> {

    @Query("SELECT * FROM ordenes WHERE estado = :estado ORDER BY fecha_ingreso DESC")
    Flux<OrdenEntity> findByEstado(String estado);

    @Query("SELECT COALESCE(SUM(cantidad * precio_unitario), 0) FROM detalle_servicios WHERE id_orden = :idOrden")
    Mono<Double> calcularTotalPorOrden(Integer idOrden);
}