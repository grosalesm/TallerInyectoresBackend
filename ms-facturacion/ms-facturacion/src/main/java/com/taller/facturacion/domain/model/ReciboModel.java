package com.taller.facturacion.domain.model;

import com.taller.facturacion.domain.constraint.ReciboConstraints;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ReciboModel implements ReciboConstraints {

    private static final List<String> METODOS_VALIDOS = Arrays.asList(
            "Efectivo", "Yape", "Plin", "Transferencia"
    );

    @Override
    public boolean validarMetodoPago(String metodoPago) {
        if (metodoPago == null || metodoPago.isBlank()) return false;
        return METODOS_VALIDOS.stream()
                .anyMatch(m -> m.equalsIgnoreCase(metodoPago));
    }

    @Override
    public boolean validarMonto(Double monto) {
        return monto != null && monto > 0;
    }
}