package com.taller.auth.infrastructure.persistence.repository;

import com.taller.auth.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsuarioR2dbcRepository extends ReactiveCrudRepository<UsuarioEntity, Integer> {

    Mono<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
}