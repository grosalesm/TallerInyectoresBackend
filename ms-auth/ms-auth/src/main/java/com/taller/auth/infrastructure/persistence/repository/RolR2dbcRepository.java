package com.taller.auth.infrastructure.persistence.repository;

import com.taller.auth.infrastructure.persistence.entity.RolEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolR2dbcRepository extends ReactiveCrudRepository<RolEntity, Integer> {
}