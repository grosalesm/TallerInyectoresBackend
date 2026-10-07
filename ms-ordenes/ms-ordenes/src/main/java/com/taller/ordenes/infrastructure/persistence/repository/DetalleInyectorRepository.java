package com.taller.ordenes.infrastructure.persistence.repository;

import com.taller.ordenes.infrastructure.persistence.entity.DetalleInyectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleInyectorRepository extends JpaRepository<DetalleInyectorEntity, Integer> {
    List<DetalleInyectorEntity> findByIdOrden(Integer idOrden);
}