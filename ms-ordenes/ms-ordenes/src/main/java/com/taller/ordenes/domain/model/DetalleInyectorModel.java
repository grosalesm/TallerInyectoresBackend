package com.taller.ordenes.domain.model;

import com.taller.ordenes.domain.bean.DetalleInyector;
import com.taller.ordenes.domain.constraint.DetalleInyectorConstraints;
import org.springframework.stereotype.Component;

@Component
public class DetalleInyectorModel implements DetalleInyectorConstraints {

    @Override
    public boolean validar(DetalleInyector detalle) {
        if (detalle == null) return false;
        if (detalle.getIdInyector() == null || detalle.getIdInyector() <= 0) return false;
        if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) return false;
        return true;
    }
}