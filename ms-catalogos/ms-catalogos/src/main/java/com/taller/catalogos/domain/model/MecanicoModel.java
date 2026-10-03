package com.taller.catalogos.domain.model;

import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.domain.constraint.MecanicoConstraints;
import org.springframework.stereotype.Component;

@Component
public class MecanicoModel implements MecanicoConstraints {

    @Override
    public boolean validar(Mecanico m) {
        if (m == null) return false;
        if (m.getNombres() == null || m.getNombres().isBlank()) return false;
        if (m.getApellidos() == null || m.getApellidos().isBlank()) return false;
        return true;
    }
}