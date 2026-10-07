package com.taller.facturacion.infrastructure.persistence.repository;

import com.taller.facturacion.infrastructure.persistence.entity.ReciboEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReciboRepository extends JpaRepository<ReciboEntity, Integer> {

    Optional<ReciboEntity> findByIdOrden(Integer idOrden);

    @Query("SELECT r FROM ReciboEntity r " +
            "WHERE MONTH(r.fechaPago) = :mes AND YEAR(r.fechaPago) = :anio " +
            "ORDER BY r.fechaPago DESC")
    List<ReciboEntity> findByMesAndAnio(@Param("mes") int mes, @Param("anio") int anio);
}