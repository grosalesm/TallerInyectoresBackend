package com.taller.clientes.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
    private Integer idCliente;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String email;
    private LocalDateTime fechaRegistro;
}