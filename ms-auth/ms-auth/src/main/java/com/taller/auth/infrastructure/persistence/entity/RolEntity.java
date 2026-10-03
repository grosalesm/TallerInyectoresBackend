package com.taller.auth.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("roles")
public class RolEntity {

    @Id
    @Column("id_rol")
    private Integer idRol;

    @Column("nombre")
    private String nombre;
}