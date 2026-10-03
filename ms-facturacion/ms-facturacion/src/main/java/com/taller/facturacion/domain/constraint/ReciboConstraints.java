package com.taller.facturacion.domain.constraint;

public interface ReciboConstraints {
    boolean validarMetodoPago(String metodoPago);
    boolean validarMonto(Double monto);
}