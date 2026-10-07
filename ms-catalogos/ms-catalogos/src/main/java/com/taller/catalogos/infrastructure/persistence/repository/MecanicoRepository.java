package com.taller.catalogos.infrastructure.persistence.repository;

import com.taller.catalogos.infrastructure.persistence.entity.MecanicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MecanicoRepository extends JpaRepository<MecanicoEntity, Integer> {
    List<MecanicoEntity> findByActivoTrue();
}