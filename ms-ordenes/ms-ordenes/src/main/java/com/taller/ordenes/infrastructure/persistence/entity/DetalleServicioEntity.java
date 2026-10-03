package com.taller.ordenes.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("detalle_servicios")
public class DetalleServicioEntity {

    @Id
    @Column("id_detalle")
    private Integer idDetalle;

    @Column("id_orden")
    private Integer idOrden;

    @Column("id_servicio")
    private Integer idServicio;

    @Column("cantidad")
    private Integer cantidad;

    @Column("precio_unitario")
    private Double precioUnitario;
}