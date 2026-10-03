package com.taller.catalogos.domain.model;

import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.domain.constraint.InyectorConstraints;
import org.springframework.stereotype.Component;

@Component
public class InyectorModel implements InyectorConstraints {

    @Override
    public boolean validar(Inyector i) {
        if (i == null) return false;
        if (i.getModelo() == null || i.getModelo().isBlank()) return false;
        return true;
    }
}