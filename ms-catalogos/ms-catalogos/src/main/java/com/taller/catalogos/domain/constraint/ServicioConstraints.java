package com.taller.catalogos.domain.constraint;

import com.taller.catalogos.domain.bean.Servicio;

public interface ServicioConstraints {
    boolean validar(Servicio servicio);
}