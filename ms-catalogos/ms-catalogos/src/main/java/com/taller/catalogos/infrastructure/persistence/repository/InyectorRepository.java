package com.taller.catalogos.infrastructure.persistence.repository;

import com.taller.catalogos.infrastructure.persistence.entity.InyectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InyectorRepository extends JpaRepository<InyectorEntity, Integer> {
    List<InyectorEntity> findByActivoTrue();
}