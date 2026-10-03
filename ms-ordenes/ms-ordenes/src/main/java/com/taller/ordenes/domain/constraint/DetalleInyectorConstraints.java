package com.taller.ordenes.domain.constraint;

import com.taller.ordenes.domain.bean.DetalleInyector;

public interface DetalleInyectorConstraints {
    boolean validar(DetalleInyector detalle);
}