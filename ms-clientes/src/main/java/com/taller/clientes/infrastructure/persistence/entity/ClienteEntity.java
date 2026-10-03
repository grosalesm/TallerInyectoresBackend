package com.taller.clientes.infrastructure.persistence.entity;

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
@Table("clientes")
public class ClienteEntity {

    @Id
    @Column("id_cliente")
    private Integer idCliente;

    @Column("nombres")
    private String nombres;

    @Column("apellidos")
    private String apellidos;

    @Column("dni")
    private String dni;

    @Column("telefono")
    private String telefono;

    @Column("email")
    private String email;

    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;
}