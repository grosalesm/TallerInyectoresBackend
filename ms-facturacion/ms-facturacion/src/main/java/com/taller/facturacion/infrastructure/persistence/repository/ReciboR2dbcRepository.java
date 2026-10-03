package com.taller.facturacion.infrastructure.persistence.repository;

import com.taller.facturacion.infrastructure.persistence.entity.ReciboEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReciboR2dbcRepository extends ReactiveCrudRepository<ReciboEntity, Integer> {

    Mono<ReciboEntity> findByIdOrden(Integer idOrden);

    @Query("SELECT * FROM recibos WHERE MONTH(fecha_pago) = :mes AND YEAR(fecha_pago) = :anio ORDER BY fecha_pago DESC")
    Flux<ReciboEntity> findByMesAndAnio(int mes, int anio);

    @Query("SELECT COUNT(*) FROM recibos")
    Mono<Long> contarRecibos();
}