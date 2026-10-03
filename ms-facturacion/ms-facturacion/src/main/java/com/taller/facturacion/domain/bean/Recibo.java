package com.taller.facturacion.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recibo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idRecibo;
    private Integer idOrden;
    private LocalDateTime fechaPago;
    private Double monto;
    private String metodoPago = "";
    private String numOperacion = "";
    private String numeroRecibo = "";
}