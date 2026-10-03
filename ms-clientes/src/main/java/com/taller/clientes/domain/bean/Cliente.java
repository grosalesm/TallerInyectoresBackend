package com.taller.clientes.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idCliente;
    private String nombres = "";
    private String apellidos = "";
    private String dni = "";
    private String telefono = "";
    private String email = "";
    private LocalDateTime fechaRegistro;
}