package com.taller.catalogos.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MecanicoResponse {
    private Integer idMecanico;
    private String nombres;
    private String apellidos;
    private String especialidad;
    private String telefono;
    private Boolean activo;
}