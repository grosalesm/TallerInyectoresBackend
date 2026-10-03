package com.taller.ordenes.domain.model;

import com.taller.ordenes.domain.bean.Orden;
import com.taller.ordenes.domain.constraint.OrdenConstraints;
import com.taller.ordenes.domain.enums.EstadoOrden;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OrdenModel implements OrdenConstraints {

    @Override
    public boolean validarCreacion(Orden orden) {
        if (orden == null) return false;
        if (orden.getIdCliente() == null || orden.getIdCliente() <= 0) return false;
        if (orden.getIdMecanico() == null || orden.getIdMecanico() <= 0) return false;
        return true;
    }

    @Override
    public boolean validarEstadoManual(String estado) {
        if (estado == null) return false;
        return Arrays.stream(EstadoOrden.values())
                .filter(e -> e != EstadoOrden.PAGADO)
                .anyMatch(e -> e.getDescripcion().equalsIgnoreCase(estado));
    }

    @Override
    public boolean validarEstadoPorEvento(String estado) {
        if (estado == null) return false;
        return Arrays.stream(EstadoOrden.values())
                .anyMatch(e -> e.getDescripcion().equalsIgnoreCase(estado));
    }
}