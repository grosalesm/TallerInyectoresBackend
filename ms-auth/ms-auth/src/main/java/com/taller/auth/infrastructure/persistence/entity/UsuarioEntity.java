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
@Table("usuarios")
public class UsuarioEntity {

    @Id
    @Column("id_usuario")
    private Integer idUsuario;

    @Column("nombre")
    private String nombre;

    @Column("nombre_usuario")
    private String nombreUsuario;

    @Column("clave")
    private String clave;

    @Column("id_rol")
    private Integer idRol;

    @Column("activo")
    private Boolean activo;
}