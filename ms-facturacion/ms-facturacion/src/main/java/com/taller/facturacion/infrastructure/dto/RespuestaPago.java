package com.taller.facturacion.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaPago {
    private String mensaje;
    private ReciboResponse recibo;
}