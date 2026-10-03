package com.taller.ordenes.domain.model;

import com.taller.ordenes.domain.bean.DetalleServicio;
import com.taller.ordenes.domain.constraint.DetalleServicioConstraints;
import org.springframework.stereotype.Component;

@Component
public class DetalleServicioModel implements DetalleServicioConstraints {

    @Override
    public boolean validar(DetalleServicio detalle) {
        if (detalle == null) return false;
        if (detalle.getIdServicio() == null || detalle.getIdServicio() <= 0) return false;
        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) return false;
        if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario() <= 0) return false;
        return true;
    }
}