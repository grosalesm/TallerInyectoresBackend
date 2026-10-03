package com.taller.catalogos.domain.constraint;

import com.taller.catalogos.domain.bean.Mecanico;

public interface MecanicoConstraints {
    boolean validar(Mecanico mecanico);
}