package com.taller.ordenes.infrastructure.persistence.repository;

import com.taller.ordenes.infrastructure.persistence.entity.OrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<OrdenEntity, Integer> {
    List<OrdenEntity> findByEstadoOrderByFechaIngresoDesc(String estado);
}