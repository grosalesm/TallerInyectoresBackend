package com.taller.catalogos.domain.model;

import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.domain.constraint.ServicioConstraints;
import org.springframework.stereotype.Component;

@Component
public class ServicioModel implements ServicioConstraints {

    @Override
    public boolean validar(Servicio s) {
        if (s == null) return false;
        if (s.getNombre() == null || s.getNombre().isBlank()) return false;
        if (s.getPrecioBase() == null || s.getPrecioBase() <= 0) return false;
        return true;
    }
}