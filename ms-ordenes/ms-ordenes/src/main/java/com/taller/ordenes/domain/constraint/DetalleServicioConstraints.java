package com.taller.ordenes.domain.constraint;

import com.taller.ordenes.domain.bean.DetalleServicio;

public interface DetalleServicioConstraints {
    boolean validar(DetalleServicio detalle);
}