package com.taller.ordenes.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("ordenes")
public class OrdenEntity {

    @Id
    @Column("id_orden")
    private Integer idOrden;

    @Column("id_cliente")
    private Integer idCliente;

    @Column("id_mecanico")
    private Integer idMecanico;

    @Column("fecha_ingreso")
    private LocalDateTime fechaIngreso;

    @Column("fecha_salida")
    private LocalDateTime fechaSalida;

    @Column("observaciones")
    private String observaciones;

    @Column("estado")
    private String estado;

    @Column("total")
    private Double total;
}