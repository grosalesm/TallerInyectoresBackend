package com.taller.catalogos.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("inyectores")
public class InyectorEntity {

    @Id
    @Column("id_inyector")
    private Integer idInyector;

    @Column("modelo")
    private String modelo;

    @Column("marca")
    private String marca;

    @Column("descripcion")
    private String descripcion;

    @Column("activo")
    private Boolean activo;
}