package com.taller.ordenes.infrastructure.persistence.repository;

import com.taller.ordenes.infrastructure.persistence.entity.DetalleServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleServicioRepository extends JpaRepository<DetalleServicioEntity, Integer> {

    List<DetalleServicioEntity> findByIdOrden(Integer idOrden);

    @Query("SELECT COALESCE(SUM(d.cantidad * d.precioUnitario), 0) " +
            "FROM DetalleServicioEntity d WHERE d.idOrden = :idOrden")
    Double calcularTotalPorOrden(@Param("idOrden") Integer idOrden);
}