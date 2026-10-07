package com.taller.auth.infrastructure.persistence.repository;

import com.taller.auth.infrastructure.persistence.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Integer> {
}