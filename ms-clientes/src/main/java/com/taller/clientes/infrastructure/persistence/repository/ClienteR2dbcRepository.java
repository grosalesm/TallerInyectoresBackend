package com.taller.clientes.infrastructure.persistence.repository;

import com.taller.clientes.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClienteR2dbcRepository extends ReactiveCrudRepository<ClienteEntity, Integer> {
    Mono<ClienteEntity> findByDni(String dni);
}