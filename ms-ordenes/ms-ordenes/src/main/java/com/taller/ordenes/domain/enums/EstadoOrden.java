package com.taller.ordenes.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoOrden {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En Proceso"),
    TERMINADO("Terminado"),
    PAGADO("Pagado");

    private final String descripcion;

    EstadoOrden(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonValue
    public String getDescripcion() {
        return descripcion;
    }

    @JsonCreator
    public static EstadoOrden fromValue(String value) {
        for (EstadoOrden e : EstadoOrden.values()) {
            if (e.descripcion.equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("EstadoOrden inválido: " + value);
    }
}