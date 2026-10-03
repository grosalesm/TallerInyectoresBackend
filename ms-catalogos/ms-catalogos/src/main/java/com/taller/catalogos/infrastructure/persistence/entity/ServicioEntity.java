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
@Table("servicios")
public class ServicioEntity {

    @Id
    @Column("id_servicio")
    private Integer idServicio;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    @Column("precio_base")
    private Double precioBase;

    @Column("activo")
    private Boolean activo;
}