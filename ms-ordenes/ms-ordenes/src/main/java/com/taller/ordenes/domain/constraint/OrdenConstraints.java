package com.taller.ordenes.domain.constraint;

import com.taller.ordenes.domain.bean.Orden;

public interface OrdenConstraints {
    boolean validarCreacion(Orden orden);
    boolean validarEstadoManual(String estado);
    boolean validarEstadoPorEvento(String estado);
}