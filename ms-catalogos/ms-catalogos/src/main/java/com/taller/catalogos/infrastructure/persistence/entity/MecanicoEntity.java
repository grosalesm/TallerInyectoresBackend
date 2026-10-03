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
@Table("mecanicos")
public class MecanicoEntity {

    @Id
    @Column("id_mecanico")
    private Integer idMecanico;

    @Column("nombres")
    private String nombres;

    @Column("apellidos")
    private String apellidos;

    @Column("especialidad")
    private String especialidad;

    @Column("telefono")
    private String telefono;

    @Column("activo")
    private Boolean activo;
}